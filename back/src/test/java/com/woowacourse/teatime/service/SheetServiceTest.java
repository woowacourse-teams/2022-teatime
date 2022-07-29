package com.woowacourse.teatime.service;

import static com.woowacourse.teatime.fixture.DomainFixture.COACH_BROWN;
import static com.woowacourse.teatime.fixture.DomainFixture.CREW;
import static com.woowacourse.teatime.fixture.DomainFixture.DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.teatime.controller.dto.response.SheetDto;
import com.woowacourse.teatime.controller.dto.response.SheetResponse;
import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Crew;
import com.woowacourse.teatime.domain.Question;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.Schedule;
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
    }

    @DisplayName("코치의 질문만큼의 시트를 만든 뒤 개수를 반환한다.")
    @Test
    void saveNewSheets() {
        questionRepository.save(new Question(coach, 1, "당신의 혈액형은?"));
        questionRepository.save(new Question(coach, 2, "당신의 별자리는?"));
        questionRepository.save(new Question(coach, 3, "당신의 mbti는?"));

        int savedSheetCount = sheetService.save(coach.getId(), reservation.getId());

        assertThat(savedSheetCount).isEqualTo(3);
    }

    @DisplayName("면담에 해당되는 시트들을 반환한다.")
    @Test
    void findByReservation() {
        questionRepository.save(new Question(coach, 1, "당신의 혈액형은?"));
        questionRepository.save(new Question(coach, 2, "당신의 별자리는?"));
        questionRepository.save(new Question(coach, 3, "당신의 mbti는?"));
        int expected = sheetService.save(coach.getId(), reservation.getId());

        SheetResponse response = sheetService.findByReservation(reservation.getId());
        List<SheetDto> sheets = response.getSheets();

        assertThat(sheets).hasSize(expected);
    }

    @DisplayName("존재하지 않는 면담 아이디로 조회하면 예외를 반환한다.")
    @Test
    void findByReservation_notFoundReservation() {
        questionRepository.save(new Question(coach, 1, "당신의 혈액형은?"));
        questionRepository.save(new Question(coach, 2, "당신의 별자리는?"));
        questionRepository.save(new Question(coach, 3, "당신의 mbti는?"));
        sheetService.save(coach.getId(), reservation.getId());

        long 존재하지_않는_아이디 = reservation.getId() + 100L;

        assertThatThrownBy(() -> sheetService.findByReservation(존재하지_않는_아이디))
                .isInstanceOf(NotFoundReservationException.class);
    }
}
