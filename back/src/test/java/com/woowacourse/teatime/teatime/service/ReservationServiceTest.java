package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.teatime.domain.ReservationStatus.APPROVED;
import static com.woowacourse.teatime.teatime.domain.SheetStatus.SUBMITTED;
import static com.woowacourse.teatime.teatime.domain.SheetStatus.WRITING;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.COACH_BROWN;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.CREW1;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.DATE_TIME;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getQuestion1;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getQuestion2;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getQuestion3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import com.woowacourse.teatime.auth.support.dto.UserRoleDto;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationApproveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindCrewHistoryResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindOwnHistoryResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachReservationsResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CrewFindOwnHistoryResponse;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Question;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.ReservationStatus;
import com.woowacourse.teatime.teatime.domain.Role;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.exception.AlreadyReservedException;
import com.woowacourse.teatime.teatime.exception.NotFoundCrewException;
import com.woowacourse.teatime.teatime.exception.NotFoundReservationException;
import com.woowacourse.teatime.teatime.exception.NotFoundRoleException;
import com.woowacourse.teatime.teatime.exception.NotFoundScheduleException;
import com.woowacourse.teatime.teatime.exception.SlackAlarmException;
import com.woowacourse.teatime.teatime.exception.UnableToSubmitSheetException;
import com.woowacourse.teatime.teatime.repository.CanceledReservationRepository;
import com.woowacourse.teatime.teatime.repository.CanceledSheetRepository;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import com.woowacourse.teatime.teatime.repository.QuestionRepository;
import com.woowacourse.teatime.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.teatime.repository.ScheduleRepository;
import com.woowacourse.teatime.teatime.repository.SheetRepository;
import com.woowacourse.teatime.teatime.service.dto.AlarmInfoDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReservationServiceTest {

    private Crew crew;
    private Coach coach;
    private Schedule schedule;

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CanceledReservationRepository canceledReservationRepository;
    @Autowired
    private CanceledSheetRepository canceledSheetRepository;
    @Autowired
    private CrewRepository crewRepository;
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @MockBean
    private AlarmService alarmService;
    @Autowired
    private SheetRepository sheetRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        crew = crewRepository.save(CREW1);
        coach = coachRepository.save(COACH_BROWN);
        schedule = scheduleRepository.save(new Schedule(coach, DATE_TIME));

        questionRepository.save(getQuestion1(coach));
        questionRepository.save(getQuestion2(coach));
        questionRepository.save(getQuestion3(coach));
    }

    @DisplayName("예약을 한다.")
    @Test
    void reserve() {
        Long reservationId = 예약에_성공한다();

        Optional<Reservation> actual = reservationRepository.findById(reservationId);

        assertTrue(actual.isPresent());
    }

    @DisplayName("예약을 할 때 크루 아이디가 존재하지 않는 아이디면 예외를 반환한다.")
    @Test
    void reserveFailNotExistedCrew() {
        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(schedule.getId());

        assertThatThrownBy(() -> reservationService.save(crew.getId() + 100L, reservationReserveRequest))
                .isInstanceOf(NotFoundCrewException.class);
    }

    @DisplayName("예약을 할 때 스케줄 아이디가 존재하지 않는 아이디면 예외를 반환한다.")
    @Test
    void reserveFailNotExistedSchedule() {
        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(
                schedule.getId() + 100L);

        assertThatThrownBy(() -> reservationService.save(crew.getId(), reservationReserveRequest))
                .isInstanceOf(NotFoundScheduleException.class);
    }

    @DisplayName("예약을 할 때 예약할 수 없는 스케줄이면 예외를 반환한다.")
    @Test
    void reserveFailAlreadyReserved() {
        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(schedule.getId());
        reservationService.save(crew.getId(), reservationReserveRequest);

        assertThatThrownBy(() -> reservationService.save(crew.getId(), reservationReserveRequest))
                .isInstanceOf(AlreadyReservedException.class);
    }

    @DisplayName("예약을 할 때 알람이 정상적으로 동작이 안되면 예외를 반환하지만 예약은 저장된다.")
    @Test
    void reserve_slackAlarmException() {
        //given
        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(schedule.getId());
        doThrow(new SlackAlarmException())
                .when(alarmService)
                .send(any(AlarmInfoDto.class), any());

        //when, then
        assertThatThrownBy(() -> reservationService.save(crew.getId(), reservationReserveRequest))
                .isInstanceOf(SlackAlarmException.class);
        boolean actual = reservationRepository.findAll()
                .stream()
                .anyMatch(reservation
                        -> reservation.getCoach().equals(coach) && reservation.getSchedule().equals(schedule));
        assertThat(actual).isTrue();
    }

    @DisplayName("면담 예약을 승인한다.")
    @Test
    void approveReservation() {
        Long reservationId = 예약에_성공한다();
        예약_승인을_확정한다(reservationId, true);

        Reservation foundReservation = reservationRepository.findById(reservationId).get();
        assertThat(foundReservation.getReservationStatus()).isEqualTo(ReservationStatus.APPROVED);
    }

    @DisplayName("예약을 승인할 때 알람이 정상적으로 동작이 안되면 예외를 반환하지만 예약은 승인된다.")
    @Test
    void approve_slackAlarmException() {
        //given
        Long reservationId = 예약에_성공한다();

        doThrow(new SlackAlarmException())
                .when(alarmService)
                .send(any(AlarmInfoDto.class), any());

        //when, then
        assertThatThrownBy(() -> 예약_승인을_확정한다(reservationId, true))
                .isInstanceOf(SlackAlarmException.class);

        ReservationStatus actual = reservationRepository.findById(reservationId)
                .get()
                .getReservationStatus();
        ReservationStatus expected = APPROVED;
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("승인 전, 면담 예약을 거절한다. -> 예약이 삭제된다.")
    @Test
    void approveReservation_deny() {
        Long reservationId = 예약에_성공한다();
        예약_승인을_확정한다(reservationId, false);

        assertAll(
                () -> assertThat(reservationRepository.findAll()).isEmpty(),
                () -> assertThat(sheetRepository.findAll()).isEmpty(),
                () -> assertThat(canceledReservationRepository.findAllByCoachId(coach.getId())).hasSize(1),
                () -> assertThat(canceledSheetRepository.findAllByOriginId(reservationId)).hasSize(3),
                () -> assertThat(schedule.getIsPossible()).isTrue()
        );
    }


    @DisplayName("코치가 승인된 예약을 취소할 수 있다. -> 예약이 삭제된다.")
    @Test
    void cancel_coach() {
        Long reservationId = 예약에_성공한다();
        예약_승인을_확정한다(reservationId, true);

        reservationService.cancel(reservationId, new UserRoleDto(coach.getId(), "COACH"));

        assertAll(
                () -> assertThat(reservationRepository.findAll()).isEmpty(),
                () -> assertThat(sheetRepository.findAll()).isEmpty(),
                () -> assertThat(canceledReservationRepository.findAllByCoachId(coach.getId())).hasSize(1),
                () -> assertThat(canceledSheetRepository.findAllByOriginId(reservationId)).hasSize(3),
                () -> assertThat(schedule.getIsPossible()).isTrue()
        );
    }

    @DisplayName("크루가 승인된 예약을 취소할 수 있다. -> 예약이 삭제된다.")
    @Test
    void cancel_crew() {
        Long reservationId = 예약에_성공한다();
        예약_승인을_확정한다(reservationId, true);

        reservationService.cancel(reservationId, new UserRoleDto(crew.getId(), "CREW"));

        assertAll(
                () -> assertThat(reservationRepository.findAll()).isEmpty(),
                () -> assertThat(sheetRepository.findAll()).isEmpty(),
                () -> assertThat(canceledReservationRepository.findAllByCrewId(crew.getId())).hasSize(1),
                () -> assertThat(canceledSheetRepository.findAllByOriginId(reservationId)).hasSize(3),
                () -> assertThat(schedule.getIsPossible()).isTrue()
        );
    }

    @DisplayName("크루가 승인되지 않은 예약을 취소 가능하다")
    @Test
    void cancel_unapprovedReservation() {
        Long reservationId = 예약에_성공한다();

        reservationService.cancel(reservationId, new UserRoleDto(crew.getId(), "CREW"));

        assertAll(
                () -> assertThat(canceledReservationRepository.findAllByCrewId(crew.getId())).isNotEmpty(),
                () -> assertThat(schedule.getIsPossible()).isTrue()
        );
    }

    @DisplayName("면담 예약을 취소할 때, 코치나 크루가 아니면 에러가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"DOCTOR", "FAKER", "ING"})
    void cancel_NotFoundRoleException(String role) {
        Long reservationId = 예약에_성공한다();
        예약_승인을_확정한다(reservationId, true);

        assertThatThrownBy(
                () -> reservationService.cancel(reservationId, new UserRoleDto(coach.getId(), role)))
                .isInstanceOf(NotFoundRoleException.class);
    }

    @DisplayName("면담 예약을 취소할 때, 예약이 없다면 에러가 발생한다.")
    @Test
    void cancel_NotFoundReservationException() {
        Long reservationId = 예약에_성공한다();
        Long 말도_안되는_아이디 = reservationId + 100L;

        assertThatThrownBy(
                () -> reservationService.cancel(말도_안되는_아이디, new UserRoleDto(coach.getId(), "CREW")))
                .isInstanceOf(NotFoundReservationException.class);
    }

    @DisplayName("예약을 취소할 때 알람이 정상적으로 동작이 안되면 예외를 반환하지만 예약은 삭제된다.")
    @Test
    void cancel_slackAlarmException() {
        //given
        Long reservationId = 예약에_성공한다();

        doThrow(new SlackAlarmException())
                .when(alarmService)
                .send(any(AlarmInfoDto.class), any());

        //when, then
        assertThatThrownBy(() -> reservationService.cancel(reservationId, new UserRoleDto(crew.getId(), "CREW")))
                .isInstanceOf(SlackAlarmException.class);
        assertThat(reservationRepository.findById(reservationId)).isEmpty();
    }

    @DisplayName("크루가 자신에 해당되는 면담 예약 목록을 조회한다.")
    @Test
    void findOwnHistoryByCrew() {
        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(schedule.getId());
        reservationService.save(crew.getId(), reservationReserveRequest);

        Schedule schedule2 = scheduleRepository.save(new Schedule(coach, DATE_TIME.minusDays(1)));
        Long reservation2Id = reservationService.save(crew.getId(), new ReservationReserveRequest(schedule2.getId()));
        reservationService.cancel(reservation2Id, new UserRoleDto(coach.getId(), Role.COACH.name()));

        List<CrewFindOwnHistoryResponse> reservations = reservationService.findOwnHistoryByCrew(crew.getId());

        assertThat(reservations).hasSize(2);
    }

    @DisplayName("코치가 크루의 히스토리를 조회한다.")
    @Test
    void findCrewHistoryByCoach() {
        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(schedule.getId());
        Long reservationId = reservationService.save(crew.getId(), reservationReserveRequest);
        예약_승인을_확정한다(reservationId, true);
        승인된_예약을_진행중인_예약으로_변경한다();
        reservationService.updateReservationStatusToDone(coach.getId(), reservationId);

        List<CoachFindCrewHistoryResponse> reservations = reservationService.findCrewHistoryByCoach(crew.getId());

        assertThat(reservations).hasSize(1);
    }

    @DisplayName("코치가 크루의 히스토리를 조회한다. -종료된 면담이 아닌 경우 조회되지 않는다.")
    @Test
    void findCrewHistoryByCoach_noDoneReservation() {
        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(schedule.getId());
        Long reservationId = reservationService.save(crew.getId(), reservationReserveRequest);
        예약_승인을_확정한다(reservationId, true);

        List<CoachFindCrewHistoryResponse> reservations = reservationService.findCrewHistoryByCoach(crew.getId());

        assertThat(reservations).hasSize(0);
    }

    @DisplayName("코치에 해당되는 면담 목록을 조회한다.")
    @Test
    void findByCoach() {
        Schedule schedule1 = scheduleRepository.save(new Schedule(coach, LocalDateTime.now().plusDays(1)));
        Schedule schedule2 = scheduleRepository.save(new Schedule(coach, LocalDateTime.now().plusDays(2)));

        Reservation reservation = new Reservation(schedule1, crew);
        reservationRepository.save(reservation);
        reservationRepository.save(new Reservation(schedule2, crew));

        reservation.confirm();

        CoachReservationsResponse coachReservationResponse = reservationService.findByCoachId(coach.getId());

        assertAll(
                () -> assertThat(coachReservationResponse.getBeforeApproved()).hasSize(1),
                () -> assertThat(coachReservationResponse.getApproved()).hasSize(1),
                () -> assertThat(coachReservationResponse.getInProgress()).hasSize(0)
        );
    }

    @DisplayName("진행중인 면담을 완료하면 완료된 상태로 바뀐다.")
    @Test
    void updateReservationStatusToDone() {
        Schedule schedule = scheduleRepository.save(new Schedule(coach, LocalDateTime.now()));
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));
        reservation.confirm();
        승인된_예약을_진행중인_예약으로_변경한다();

        reservationService.updateReservationStatusToDone(coach.getId(), reservation.getId());
        CoachReservationsResponse response = reservationService.findByCoachId(coach.getId());

        assertAll(
                () -> assertThat(response.getBeforeApproved()).hasSize(0),
                () -> assertThat(response.getApproved()).hasSize(0),
                () -> assertThat(response.getInProgress()).hasSize(0)
        );
    }

    @DisplayName("면담 시트를 임시저장하면 작성중 상태로 유지된다.")
    @Test
    void updateSheetStatus_toWriting() {
        Schedule schedule = scheduleRepository.save(new Schedule(coach, LocalDateTime.now()));
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));

        reservationService.updateSheetStatusToSubmitted(crew.getId(), reservation.getId(), WRITING);

        assertThat(reservation.getSheetStatus()).isEqualTo(WRITING);
    }

    @DisplayName("면담 시트를 제출된 상태로 변경한다.")
    @Test
    void updateSheetStatus_toSubmitted() {
        Schedule schedule = scheduleRepository.save(new Schedule(coach, LocalDateTime.now()));
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));

        reservationService.updateSheetStatusToSubmitted(crew.getId(), reservation.getId(), SUBMITTED);

        assertThat(reservation.getSheetStatus()).isEqualTo(SUBMITTED);
    }

    @DisplayName("이미 제출된 면담 시트를 제출하면 예외를 반환한다.")
    @Test
    void updateSheetStatus_toSubmitted_alreadySubmitted() {
        Schedule schedule = scheduleRepository.save(new Schedule(coach, LocalDateTime.now()));
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));

        reservationService.updateSheetStatusToSubmitted(crew.getId(), reservation.getId(), SUBMITTED);

        assertThatThrownBy(
                () -> reservationService.updateSheetStatusToSubmitted(crew.getId(), reservation.getId(), SUBMITTED))
                .isInstanceOf(UnableToSubmitSheetException.class);
    }

    @DisplayName("승인된 예약을 진행중인 예약으로 변경한다.")
    @Test
    void updateReservationStatusToInProgress() {
        // given
        Schedule schedule = scheduleRepository.save(new Schedule(coach, LocalDateTime.now()));
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));
        reservation.confirm();

        // when
        승인된_예약을_진행중인_예약으로_변경한다();

        // then
        Reservation savedReservation = reservationRepository.findById(reservation.getId())
                .orElseThrow();
        ReservationStatus actual = savedReservation.getReservationStatus();
        assertThat(actual).isEqualTo(ReservationStatus.IN_PROGRESS);
    }

    @DisplayName("코치가 자신에 해당되는 취소, 완료 상태의 면담 예약 목록을 조회한다.")
    @Test
    void findOwnHistoryByCoach() {
        Schedule schedule1 = scheduleRepository.save(new Schedule(coach, DATE_TIME));
        reservationService.save(crew.getId(), new ReservationReserveRequest(schedule1.getId()));

        Schedule schedule2 = scheduleRepository.save(new Schedule(coach, DATE_TIME.minusDays(1)));
        Long reservation2Id = reservationService.save(crew.getId(), new ReservationReserveRequest(schedule2.getId()));
        reservationService.cancel(reservation2Id, new UserRoleDto(coach.getId(), Role.COACH.name()));

        Schedule schedule3 = scheduleRepository.save(new Schedule(coach, DATE_TIME.minusDays(2)));
        Long reservation3Id = reservationService.save(crew.getId(), new ReservationReserveRequest(schedule3.getId()));
        Reservation reservation3 = reservationRepository.findById(reservation3Id).get();
        reservation3.confirm();
        reservation3.updateSheetStatusToSubmitted();
        reservation3.updateReservationStatusToInProgress();
        reservation3.updateReservationStatusToDone();

        List<CoachFindOwnHistoryResponse> history = reservationService.findOwnHistoryByCoach(coach.getId());

        assertThat(history).hasSize(2);
    }

    private Long 예약에_성공한다() {
        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(schedule.getId());
        return reservationService.save(crew.getId(), reservationReserveRequest);
    }

    private void 예약_승인을_확정한다(Long reservationId, boolean isApproved) {
        reservationService.approve(coach.getId(), reservationId, new ReservationApproveRequest(isApproved));
    }

    private void 승인된_예약을_진행중인_예약으로_변경한다() {
        reservationService.updateReservationStatusToInProgress();
    }
}
