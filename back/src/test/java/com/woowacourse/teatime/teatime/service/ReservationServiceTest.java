package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.teatime.domain.SheetStatus.SUBMITTED;
import static com.woowacourse.teatime.teatime.domain.SheetStatus.WRITING;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.COACH_BROWN;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.COACH_JASON;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.CREW;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.woowacourse.teatime.teatime.controller.dto.request.ReservationApproveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindCrewHistoryResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachReservationsResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CrewFindOwnHistoryResponse;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.ReservationStatus;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.exception.AlreadyReservedException;
import com.woowacourse.teatime.teatime.exception.NotFoundCrewException;
import com.woowacourse.teatime.teatime.exception.NotFoundReservationException;
import com.woowacourse.teatime.teatime.exception.NotFoundRoleException;
import com.woowacourse.teatime.teatime.exception.NotFoundScheduleException;
import com.woowacourse.teatime.teatime.exception.UnCancellableReservationException;
import com.woowacourse.teatime.teatime.exception.UnableToSubmitSheetException;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import com.woowacourse.teatime.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.teatime.repository.ScheduleRepository;
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
    private CrewRepository crewRepository;
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @BeforeEach
    void setUp() {
        crew = crewRepository.save(CREW);
        coach = coachRepository.save(COACH_BROWN);
        schedule = scheduleRepository.save(new Schedule(coach, DATE_TIME));
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
        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(crew.getId() + 100L,
                coach.getId(),
                schedule.getId());
        assertThatThrownBy(() -> reservationService.save(reservationReserveRequest))
                .isInstanceOf(NotFoundCrewException.class);
    }

    @DisplayName("예약을 할 때 스케줄 아이디가 존재하지 않는 아이디면 예외를 반환한다.")
    @Test
    void reserveFailNotExistedSchedule() {
        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(crew.getId(), coach.getId(),
                schedule.getId() + 100L);
        assertThatThrownBy(() -> reservationService.save(reservationReserveRequest))
                .isInstanceOf(NotFoundScheduleException.class);
    }

    @DisplayName("예약을 할 때 코치 아이디가 일치하지 않으면 예외를 반환한다.")
    @Test
    void reserveFailNotMatchedCoach() {
        Coach differentCoach = coachRepository.save(COACH_JASON);

        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(crew.getId(),
                differentCoach.getId(),
                schedule.getId());
        assertThatThrownBy(() -> reservationService.save(reservationReserveRequest))
                .isInstanceOf(NotFoundScheduleException.class);
    }

    @DisplayName("예약을 할 때 예약할 수 없는 스케줄이면 예외를 반환한다.")
    @Test
    void reserveFailAlreadyReserved() {
        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(crew.getId(), coach.getId(),
                schedule.getId());
        reservationService.save(reservationReserveRequest);

        assertThatThrownBy(() -> reservationService.save(reservationReserveRequest))
                .isInstanceOf(AlreadyReservedException.class);
    }

    @DisplayName("면담 예약을 승인한다.")
    @Test
    void approveReservation() {
        Long reservationId = 예약에_성공한다();
        예약_승인을_확정한다(reservationId, true);

        Reservation foundReservation = reservationRepository.findById(reservationId).get();
        assertThat(foundReservation.getReservationStatus()).isEqualTo(ReservationStatus.APPROVED);
    }

    @DisplayName("승인 전, 면담 예약을 거절한다. -> 예약이 삭제된다.")
    @Test
    void approveReservation_deny() {
        Long reservationId = 예약에_성공한다();
        예약_승인을_확정한다(reservationId, false);

        assertAll(
                () -> assertThat(reservationRepository.findById(reservationId)).isEmpty(),
                () -> assertThat(schedule.getIsPossible()).isTrue()
        );
    }


    @DisplayName("코치가 면담 예약을 취소할 수 있다. -> 예약이 삭제된다.")
    @Test
    void cancel_coach() {
        Long reservationId = 예약에_성공한다();
        예약_승인을_확정한다(reservationId, true);

        reservationService.cancel(reservationId, coach.getId(), "COACH");

        assertAll(
                () -> assertThat(reservationRepository.findById(reservationId)).isEmpty(),
                () -> assertThat(schedule.getIsPossible()).isTrue()
        );
    }

    @DisplayName("크루가 면담 예약을 취소할 수 있다. -> 예약이 삭제된다.")
    @Test
    void cancel_crew() {
        Long reservationId = 예약에_성공한다();
        예약_승인을_확정한다(reservationId, true);

        reservationService.cancel(reservationId, crew.getId(), "CREW");

        assertAll(
                () -> assertThat(reservationRepository.findById(reservationId)).isEmpty(),
                () -> assertThat(schedule.getIsPossible()).isTrue()
        );
    }

    @DisplayName("크루가 승인되지 않은 예약을 취소 가능하다")
    @Test
    void cancel_unapprovedReservation() {
        Long reservationId = 예약에_성공한다();

        reservationService.cancel(reservationId, crew.getId(), "CREW");

        assertAll(
                () -> assertThat(reservationRepository.findById(reservationId)).isEmpty(),
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
                () -> reservationService.cancel(reservationId, coach.getId(), role))
                .isInstanceOf(NotFoundRoleException.class);
    }

    @DisplayName("코치가 면담 예약을 취소할 때, 승인되지 않은 예약이면 에러가 발생한다.")
    @Test
    void cancel_InvalidCancelException() {
        Long reservationId = 예약에_성공한다();

        assertThatThrownBy(
                () -> reservationService.cancel(reservationId, coach.getId(), "COACH"))
                .isInstanceOf(UnCancellableReservationException.class);
    }

    @DisplayName("면담 예약을 취소할 때, 예약이 없다면 에러가 발생한다.")
    @Test
    void cancel_NotFoundReservationException() {
        Long reservationId = 예약에_성공한다();
        Long 말도_안되는_아이디 = reservationId + 100L;

        assertThatThrownBy(
                () -> reservationService.cancel(말도_안되는_아이디, coach.getId(), "CREW"))
                .isInstanceOf(NotFoundReservationException.class);
    }

    @DisplayName("크루가 자신에 해당되는 면담 예약 목록을 조회한다.")
    @Test
    void findOwnHistoryByCrew() {
        ReservationReserveRequest reserveRequest = new ReservationReserveRequest(crew.getId(), coach.getId(),
                schedule.getId());
        reservationService.save(reserveRequest);

        List<CrewFindOwnHistoryResponse> reservations = reservationService.findOwnHistoryByCrew(crew.getId());

        assertThat(reservations).hasSize(1);
    }

    @DisplayName("코치가 크루의 히스토리를 조회한다.")
    @Test
    void findCrewHistoryByCoach() {
        ReservationReserveRequest reserveRequest = new ReservationReserveRequest(crew.getId(), coach.getId(),
                schedule.getId());
        Long reservationId = reservationService.save(reserveRequest);
        reservationService.approve(reservationId, new ReservationApproveRequest(coach.getId(), true));
        reservationService.findByCoachId(coach.getId());
        reservationService.updateReservationStatusToDone(reservationId);

        List<CoachFindCrewHistoryResponse> reservations = reservationService.findCrewHistoryByCoach(crew.getId());

        assertThat(reservations).hasSize(1);
    }

    @DisplayName("코치가 크루의 히스토리를 조회한다. -종료된 면담이 아닌 경우 조회되지 않는다.")
    @Test
    void findCrewHistoryByCoach_noDoneReservation() {
        ReservationReserveRequest reserveRequest = new ReservationReserveRequest(crew.getId(), coach.getId(),
                schedule.getId());
        Long reservationId = reservationService.save(reserveRequest);
        reservationService.approve(reservationId, new ReservationApproveRequest(coach.getId(), true));

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

        reservation.confirm(true);

        CoachReservationsResponse coachReservationResponse = reservationService.findByCoachId(coach.getId());

        assertAll(
                () -> assertThat(coachReservationResponse.getBeforeApproved()).hasSize(1),
                () -> assertThat(coachReservationResponse.getApproved()).hasSize(1),
                () -> assertThat(coachReservationResponse.getInProgress()).hasSize(0)
        );
    }

    @DisplayName("코치에 해당되는 면담 목록을 조회할 때 승인된 일정은 면담이 시작되면 진행중으로 바뀐다.")
    @Test
    void findByCoachStatusUpdate() {
        Schedule schedule = scheduleRepository.save(new Schedule(coach, LocalDateTime.now()));
        Schedule schedule2 = scheduleRepository.save(new Schedule(coach, LocalDateTime.now().plusDays(1)));
        Reservation reservation = new Reservation(schedule, crew);
        Reservation reservation2 = new Reservation(schedule2, crew);
        reservationRepository.save(reservation);
        reservationRepository.save(reservation2);
        reservation.confirm(true);
        reservation2.confirm(true);

        CoachReservationsResponse response = reservationService.findByCoachId(coach.getId());

        assertAll(
                () -> assertThat(response.getBeforeApproved()).hasSize(0),
                () -> assertThat(response.getApproved()).hasSize(1),
                () -> assertThat(response.getInProgress()).hasSize(1)
        );
    }

    private Long 예약에_성공한다() {
        ReservationReserveRequest reservationRequest = new ReservationReserveRequest(crew.getId(), coach.getId(),
                schedule.getId());
        return reservationService.save(reservationRequest);
    }

    private void 예약_승인을_확정한다(Long reservationId, boolean isApproved) {
        ReservationApproveRequest reservationApproveRequest = new ReservationApproveRequest(coach.getId(), isApproved);
        reservationService.approve(reservationId, reservationApproveRequest);
    }

    @DisplayName("진행중인 면담을 완료하면 완료된 상태로 바뀐다.")
    @Test
    void updateReservationStatusToDone() {
        Schedule schedule = scheduleRepository.save(new Schedule(coach, LocalDateTime.now()));
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));
        reservation.confirm(true);
        reservationService.findByCoachId(coach.getId());

        reservationService.updateReservationStatusToDone(reservation.getId());
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

        reservationService.updateSheetStatusToSubmitted(reservation.getId(), WRITING);

        assertThat(reservation.getSheetStatus()).isEqualTo(WRITING);
    }

    @DisplayName("면담 시트를 제출된 상태로 변경한다.")
    @Test
    void updateSheetStatus_toSubmitted() {
        Schedule schedule = scheduleRepository.save(new Schedule(coach, LocalDateTime.now()));
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));

        reservationService.updateSheetStatusToSubmitted(reservation.getId(), SUBMITTED);

        assertThat(reservation.getSheetStatus()).isEqualTo(SUBMITTED);
    }

    @DisplayName("이미 제출된 면담 시트를 제출하면 예외를 반환한다.")
    @Test
    void updateSheetStatus_toSubmitted_alreadySubmitted() {
        Schedule schedule = scheduleRepository.save(new Schedule(coach, LocalDateTime.now()));
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));

        reservationService.updateSheetStatusToSubmitted(reservation.getId(), SUBMITTED);

        assertThatThrownBy(() -> reservationService.updateSheetStatusToSubmitted(reservation.getId(), SUBMITTED))
                .isInstanceOf(UnableToSubmitSheetException.class);
    }
}