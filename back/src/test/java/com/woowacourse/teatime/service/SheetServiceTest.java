package com.woowacourse.teatime.service;

import static com.woowacourse.teatime.fixture.DomainFixture.COACH_BROWN;
import static com.woowacourse.teatime.fixture.DomainFixture.CREW;
import static com.woowacourse.teatime.fixture.DomainFixture.DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.teatime.controller.dto.request.SheetAnswerUpdateDto;
import com.woowacourse.teatime.controller.dto.request.SheetAnswerUpdateRequest;
import com.woowacourse.teatime.controller.dto.response.CoachFindCrewSheetResponse;
import com.woowacourse.teatime.controller.dto.response.CrewFindOwnSheetResponse;
import com.woowacourse.teatime.controller.dto.response.SheetDto;
import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Crew;
import com.woowacourse.teatime.domain.Question;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.domain.SheetStatus;
import com.woowacourse.teatime.exception.NotFoundCrewException;
import com.woowacourse.teatime.exception.NotFoundReservationException;
import com.woowacourse.teatime.repository.CoachRepository;
import com.woowacourse.teatime.repository.CrewRepository;
import com.woowacourse.teatime.repository.QuestionRepository;
import com.woowacourse.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.repository.ScheduleRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
class SheetServiceTest {

    private Crew crew;
    private Coach coach;
    private Schedule schedule;
    private Reservation reservation;

    @Autowired
    private SheetService sheetService;
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private CrewRepository crewRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        crew = crewRepository.save(CREW);
        coach = coachRepository.save(COACH_BROWN);
        schedule = scheduleRepository.save(new Schedule(coach, DATE_TIME));
        reservation = reservationRepository.save(new Reservation(schedule, crew));

        questionRepository.save(new Question(coach, 1, "당신의 혈액형은?"));
        questionRepository.save(new Question(coach, 2, "당신의 별자리는?"));
        questionRepository.save(new Question(coach, 3, "당신의 mbti는?"));
    }

    @DisplayName("코치의 질문만큼의 시트를 만든 뒤 개수를 반환한다.")
    @Test
    void saveNewSheets() {
        int savedSheetCount = sheetService.save(coach.getId(), reservation.getId());

        assertThat(savedSheetCount).isEqualTo(3);
    }

    @DisplayName("크루가 자신의 면담 시트 조회 - 면담에 해당되는 시트들을 반환한다.")
    @Test
    void findOwnSheetByCrew() {
        int expected = sheetService.save(coach.getId(), reservation.getId());

        CrewFindOwnSheetResponse response = sheetService.findOwnSheetByCrew(reservation.getId());
        List<SheetDto> sheets = response.getSheets();

        assertThat(sheets).hasSize(expected);
    }

    @DisplayName("크루가 자신의 면담 시트 조회 - 존재하지 않는 면담 아이디로 조회하면 예외를 반환한다.")
    @Test
    void findOwnSheetByCrew_notFoundReservation() {
        sheetService.save(coach.getId(), reservation.getId());

        long 존재하지_않는_아이디 = reservation.getId() + 100L;

        assertThatThrownBy(() -> sheetService.findOwnSheetByCrew(존재하지_않는_아이디))
                .isInstanceOf(NotFoundReservationException.class);
    }

    @DisplayName("코치가 크루의 면담 시트 조회 - 면담에 해당되는 시트들을 반환한다.")
    @Test
    void findCrewSheetByCoach() {
        int expected = sheetService.save(coach.getId(), reservation.getId());

        CoachFindCrewSheetResponse response = sheetService.findCrewSheetByCoach(crew.getId(), reservation.getId());
        List<SheetDto> sheets = response.getSheets();

        assertThat(sheets).hasSize(expected);
    }

    @DisplayName("코치가 크루의 면담 시트 조회 - 존재하지 않는 크루 아이디로 조회하면 예외를 반환한다.")
    @Test
    void findCrewSheetByCoach_notFoundCrew() {
        sheetService.save(coach.getId(), reservation.getId());

        long 존재하지_않는_아이디 = crew.getId() + 100L;

        assertThatThrownBy(() -> sheetService.findCrewSheetByCoach(존재하지_않는_아이디, reservation.getId()))
                .isInstanceOf(NotFoundCrewException.class);
    }

    @DisplayName("코치가 크루의 면담 시트 조회 - 존재하지 않는 면담 아이디로 조회하면 예외를 반환한다.")
    @Test
    void findCrewSheetByCoach_notFoundReservation() {
        sheetService.save(coach.getId(), reservation.getId());

        long 존재하지_않는_아이디 = reservation.getId() + 100L;

        assertThatThrownBy(() -> sheetService.findCrewSheetByCoach(crew.getId(), 존재하지_않는_아이디))
                .isInstanceOf(NotFoundReservationException.class);
    }

    @DisplayName("면담 시트의 답변을 수정한다.")
    @Test
    void modifyAnswer() {
        sheetService.save(coach.getId(), reservation.getId());

        List<SheetAnswerUpdateDto> updateDtos = List.of(
                new SheetAnswerUpdateDto(2, "물고기 자리"),
                new SheetAnswerUpdateDto(1, "B형"),
                new SheetAnswerUpdateDto(3, "entp"));
        SheetAnswerUpdateRequest request = new SheetAnswerUpdateRequest(SheetStatus.WRITING, updateDtos);
        sheetService.updateAnswer(reservation.getId(), request);

        CoachFindCrewSheetResponse response = sheetService.findCrewSheetByCoach(crew.getId(), reservation.getId());
        List<SheetDto> sheets = response.getSheets();
        assertAll(
                () -> assertThat(sheets.get(0).getAnswerContent()).isEqualTo("B형"),
                () -> assertThat(sheets.get(1).getAnswerContent()).isEqualTo("물고기 자리"),
                () -> assertThat(sheets.get(2).getAnswerContent()).isEqualTo("entp")
        );

    }
}
