package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.teatime.domain.SheetStatus.SUBMITTED;
import static com.woowacourse.teatime.teatime.domain.SheetStatus.WRITING;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.COACH_BROWN;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.CREW1;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.DATE_TIME;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getQuestion1;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getQuestion2;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getQuestion3;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getQuestionIsRequiredFalse;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.SHEET_ANSWER_UPDATE_REQUEST_ONE;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.SHEET_ANSWER_UPDATE_REQUEST_THREE;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.SHEET_ANSWER_UPDATE_REQUEST_TWO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.teatime.auth.support.dto.UserRoleDto;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.SheetAnswerUpdateDto;
import com.woowacourse.teatime.teatime.controller.dto.request.SheetAnswerUpdateRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindCrewSheetResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CrewFindOwnCanceledSheetResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CrewFindOwnSheetResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.SheetDto;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.Role;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.exception.CannotSubmitBlankException;
import com.woowacourse.teatime.teatime.exception.NotFoundCrewException;
import com.woowacourse.teatime.teatime.exception.NotFoundReservationException;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import com.woowacourse.teatime.teatime.repository.QuestionRepository;
import com.woowacourse.teatime.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.teatime.repository.ScheduleRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
    private ReservationService reservationService;
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
        crew = crewRepository.save(CREW1);
        coach = coachRepository.save(COACH_BROWN);
        schedule = scheduleRepository.save(new Schedule(coach, DATE_TIME));
        reservation = reservationRepository.save(new Reservation(schedule, crew));

        questionRepository.save(getQuestion1(coach));
        questionRepository.save(getQuestion2(coach));
        questionRepository.save(getQuestion3(coach));
    }

    @DisplayName("코치의 질문만큼의 시트를 만든 뒤 개수를 반환한다.")
    @Test
    void saveNewSheets() {
        int savedSheetCount = sheetService.save(reservation.getId());

        assertThat(savedSheetCount).isEqualTo(3);
    }

    @DisplayName("크루가 자신의 면담 시트 조회 - 면담에 해당되는 시트들을 반환한다.")
    @Test
    void findOwnSheetByCrew() {
        //given
        int expected = sheetService.save(reservation.getId());

        //when
        CrewFindOwnSheetResponse response = sheetService.findOwnSheetByCrew(crew.getId(), reservation.getId());

        //then
        List<SheetDto> sheets = response.getSheets();
        assertThat(sheets).hasSize(expected);
    }

    @DisplayName("크루가 자신의 면담 시트 조회 - 면담에 해당되는 시트들을 반환한다.")
    @Test
    void findOwnSheetByCrew_필수_여부_확인() {
        //given
        int expected = sheetService.save(reservation.getId());

        //when
        CrewFindOwnSheetResponse response = sheetService.findOwnSheetByCrew(crew.getId(), reservation.getId());

        //then
        List<SheetDto> sheets = response.getSheets();
        List<Boolean> isRequired = sheets.stream()
                .map(SheetDto::getIsRequired)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(sheets).hasSize(expected),
                () -> assertThat(isRequired).containsOnly(true, true, true)
        );
    }

    @DisplayName("크루가 자신의 면담 시트 조회 - 면담에 해당되는 시트들을 반환한다.")
    @Test
    void findOwnSheetByCrew_필수_여부_확인_false가_포함된_경우() {
        //given
        questionRepository.save(getQuestionIsRequiredFalse(coach));
        int expected = sheetService.save(reservation.getId());

        //when
        CrewFindOwnSheetResponse response = sheetService.findOwnSheetByCrew(crew.getId(), reservation.getId());

        //then
        List<SheetDto> sheets = response.getSheets();
        List<Boolean> isRequired = sheets.stream()
                .map(SheetDto::getIsRequired)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(sheets).hasSize(expected),
                () -> assertThat(isRequired).containsOnly(true, true, true, false)
        );
    }

    @DisplayName("크루가 자신의 면담 시트 조회 - 존재하지 않는 면담 아이디로 조회하면 예외를 반환한다.")
    @Test
    void findOwnSheetByCrew_notFoundReservation() {
        sheetService.save(reservation.getId());

        long 존재하지_않는_아이디 = reservation.getId() + 100L;

        assertThatThrownBy(() -> sheetService.findOwnSheetByCrew(crew.getId(), 존재하지_않는_아이디))
                .isInstanceOf(NotFoundReservationException.class);
    }

    @DisplayName("크루가 자신의 취소된 면담 시트를 조회한다.")
    @Test
    void findOwnCanceledSheetByCrew() {
        //given
        Schedule schedule1 = scheduleRepository.save(new Schedule(coach, DATE_TIME));
        Long reservationId = reservationService.save(crew.getId(), new ReservationReserveRequest(schedule1.getId()));
        reservationService.cancel(reservationId, new UserRoleDto(coach.getId(), Role.COACH.name()));

        //when
        CrewFindOwnCanceledSheetResponse canceledSheet = sheetService.findOwnCanceledSheetByCrew(crew.getId(),
                reservationId);

        //then
        List<SheetDto> sheets = canceledSheet.getSheets();
        List<Boolean> isRequired = sheets.stream()
                .map(SheetDto::getIsRequired)
                .collect(Collectors.toList());
        assertAll(
                () -> assertThat(sheets).hasSize(3),
                () -> assertThat(isRequired).containsOnly(true, true, true)
        );
    }

    @DisplayName("코치가 크루의 면담 시트 조회 - 면담에 해당되는 시트들을 반환한다.")
    @Test
    void findCrewSheetByCoach() {
        int expected = sheetService.save(reservation.getId());

        CoachFindCrewSheetResponse response = sheetService.findCrewSheetByCoach(crew.getId(), reservation.getId());
        List<SheetDto> sheets = response.getSheets();

        assertThat(sheets).hasSize(expected);
    }

    @DisplayName("코치가 크루의 면담 시트 조회 - 면담에 해당되는 시트들을 반환한다.")
    @Test
    void findCrewSheetByCoach_필수_여부_확인() {
        //given
        int expected = sheetService.save(reservation.getId());

        //when
        CoachFindCrewSheetResponse response = sheetService.findCrewSheetByCoach(crew.getId(), reservation.getId());

        //then
        List<SheetDto> sheets = response.getSheets();
        List<Boolean> isRequired = sheets.stream()
                .map(SheetDto::getIsRequired)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(sheets).hasSize(expected),
                () -> assertThat(isRequired).containsOnly(true, true, true)
        );
    }

    @DisplayName("코치가 크루의 면담 시트 조회 - 존재하지 않는 크루 아이디로 조회하면 예외를 반환한다.")
    @Test
    void findCrewSheetByCoach_notFoundCrew() {
        sheetService.save(reservation.getId());

        long 존재하지_않는_아이디 = crew.getId() + 100L;

        assertThatThrownBy(() -> sheetService.findCrewSheetByCoach(존재하지_않는_아이디, reservation.getId()))
                .isInstanceOf(NotFoundCrewException.class);
    }

    @DisplayName("코치가 크루의 면담 시트 조회 - 존재하지 않는 면담 아이디로 조회하면 예외를 반환한다.")
    @Test
    void findCrewSheetByCoach_notFoundReservation() {
        sheetService.save(reservation.getId());

        long 존재하지_않는_아이디 = reservation.getId() + 100L;

        assertThatThrownBy(() -> sheetService.findCrewSheetByCoach(crew.getId(), 존재하지_않는_아이디))
                .isInstanceOf(NotFoundReservationException.class);
    }

    @DisplayName("면담 시트의 답변을 임시저장한다. - 코치가 조회하면 답변이 null")
    @Test
    void modifyAnswer_write_findByCoach() {
        sheetService.save(reservation.getId());

        List<SheetAnswerUpdateDto> updateDtos = List.of(
                SHEET_ANSWER_UPDATE_REQUEST_TWO,
                SHEET_ANSWER_UPDATE_REQUEST_ONE,
                SHEET_ANSWER_UPDATE_REQUEST_THREE);
        SheetAnswerUpdateRequest request = new SheetAnswerUpdateRequest(WRITING, updateDtos);
        sheetService.updateAnswer(crew.getId(), reservation.getId(), request);

        CoachFindCrewSheetResponse response = sheetService.findCrewSheetByCoach(crew.getId(), reservation.getId());
        List<SheetDto> sheets = response.getSheets();
        assertAll(
                () -> assertThat(sheets.get(0).getAnswerContent()).isNull(),
                () -> assertThat(sheets.get(1).getAnswerContent()).isNull(),
                () -> assertThat(sheets.get(2).getAnswerContent()).isNull()
        );
    }

    @DisplayName("면담 시트의 답변을 임시저장한다. - 크루가 조회하면 답변이 보임")
    @Test
    void modifyAnswer_write_findByCrew() {
        sheetService.save(reservation.getId());

        List<SheetAnswerUpdateDto> updateDtos = List.of(
                SHEET_ANSWER_UPDATE_REQUEST_TWO,
                SHEET_ANSWER_UPDATE_REQUEST_ONE,
                SHEET_ANSWER_UPDATE_REQUEST_THREE);
        SheetAnswerUpdateRequest request = new SheetAnswerUpdateRequest(WRITING, updateDtos);
        sheetService.updateAnswer(crew.getId(), reservation.getId(), request);

        CrewFindOwnSheetResponse response = sheetService.findOwnSheetByCrew(crew.getId(), reservation.getId());
        List<SheetDto> sheets = response.getSheets();
        assertAll(
                () -> assertThat(sheets.get(0).getAnswerContent()).isEqualTo("B형"),
                () -> assertThat(sheets.get(1).getAnswerContent()).isEqualTo("물고기 자리"),
                () -> assertThat(sheets.get(2).getAnswerContent()).isEqualTo("entp")
        );
    }

    @DisplayName("면담 시트의 답변을 제출한다.")
    @Test
    void modifyAnswer_submit() {
        sheetService.save(reservation.getId());

        List<SheetAnswerUpdateDto> updateDtos = List.of(
                SHEET_ANSWER_UPDATE_REQUEST_TWO,
                SHEET_ANSWER_UPDATE_REQUEST_ONE,
                SHEET_ANSWER_UPDATE_REQUEST_THREE);
        SheetAnswerUpdateRequest request = new SheetAnswerUpdateRequest(SUBMITTED, updateDtos);
        sheetService.updateAnswer(crew.getId(), reservation.getId(), request);
        reservationService.updateSheetStatusToSubmitted(crew.getId(), reservation.getId(), request.getStatus());

        CoachFindCrewSheetResponse response = sheetService.findCrewSheetByCoach(crew.getId(), reservation.getId());
        List<SheetDto> sheets = response.getSheets();
        assertAll(
                () -> assertThat(sheets.get(0).getAnswerContent()).isEqualTo("B형"),
                () -> assertThat(sheets.get(1).getAnswerContent()).isEqualTo("물고기 자리"),
                () -> assertThat(sheets.get(2).getAnswerContent()).isEqualTo("entp")
        );
    }

    @DisplayName("면담 시트의 답변 제출에 실패한다. - 공백을 포함하는 답변이 있는 경우")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void modifyAnswer_submit_blankException(String answer) {
        sheetService.save(reservation.getId());

        List<SheetAnswerUpdateDto> updateDtos = List.of(
                new SheetAnswerUpdateDto(2, "당신의 별자리는?", answer, true),
                SHEET_ANSWER_UPDATE_REQUEST_ONE,
                SHEET_ANSWER_UPDATE_REQUEST_THREE);
        SheetAnswerUpdateRequest request = new SheetAnswerUpdateRequest(SUBMITTED, updateDtos);

        assertThatThrownBy(() -> sheetService.updateAnswer(crew.getId(), reservation.getId(), request))
                .isInstanceOf(CannotSubmitBlankException.class);
    }

    @DisplayName("면담 시트의 답변 제출에 실패한다. - 공백을 포함하는 답변이 있는 경우")
    @Test
    void modifyAnswer_submit_nullException() {
        sheetService.save(reservation.getId());

        List<SheetAnswerUpdateDto> updateDtos = List.of(
                new SheetAnswerUpdateDto(2, "당신의 별자리는?", null, true),
                SHEET_ANSWER_UPDATE_REQUEST_ONE,
                SHEET_ANSWER_UPDATE_REQUEST_THREE);
        SheetAnswerUpdateRequest request = new SheetAnswerUpdateRequest(SUBMITTED, updateDtos);

        assertThatThrownBy(() -> sheetService.updateAnswer(crew.getId(), reservation.getId(), request))
                .isInstanceOf(CannotSubmitBlankException.class);
    }
}
