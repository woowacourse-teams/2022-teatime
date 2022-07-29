package com.woowacourse.teatime.service;

import static com.woowacourse.teatime.fixture.DomainFixture.COACH_BROWN;
import static com.woowacourse.teatime.fixture.DomainFixture.CREW;
import static com.woowacourse.teatime.fixture.DomainFixture.DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.woowacourse.teatime.controller.dto.request.ReservationApproveRequest;
import com.woowacourse.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.controller.dto.response.CrewHistoryFindResponse;
import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Crew;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.ReservationStatus;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.exception.AlreadyReservedException;
import com.woowacourse.teatime.exception.NotFoundCrewException;
import com.woowacourse.teatime.exception.NotFoundScheduleException;
import com.woowacourse.teatime.repository.CoachRepository;
import com.woowacourse.teatime.repository.CrewRepository;
import com.woowacourse.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.repository.ScheduleRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(crew.getId(), coach.getId(),
                schedule.getId());
        Long reservationId = reservationService.save(reservationReserveRequest);

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
        Coach fakeCoach = coachRepository.save(new Coach("ori"));

        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(crew.getId(),
                fakeCoach.getId(),
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
        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(crew.getId(), coach.getId(),
                schedule.getId());
        Long reservationId = reservationService.save(reservationReserveRequest);
        ReservationApproveRequest reservationApproveRequest = new ReservationApproveRequest(coach.getId(), true);

        reservationService.approve(reservationId, reservationApproveRequest);

        Reservation foundReservation = reservationRepository.findById(reservationId).get();
        assertThat(foundReservation.getStatus()).isEqualTo(ReservationStatus.APPROVED);
    }

    @DisplayName("승인 전, 면담 예약을 거절한다. -> 예약이 삭제된다.")
    @Test
    void approveReservation_deny() {
        ReservationReserveRequest reserveRequest = new ReservationReserveRequest(crew.getId(), coach.getId(),
                schedule.getId());
        Long reservationId = reservationService.save(reserveRequest);
        ReservationApproveRequest approveRequest = new ReservationApproveRequest(coach.getId(), false);

        reservationService.approve(reservationId, approveRequest);

        assertAll(
                () -> assertThat(reservationRepository.findById(reservationId)).isEmpty(),
                () -> assertThat(schedule.getIsPossible()).isTrue()
        );
    }

    @DisplayName("크루에 해당되는 면담 예약 목록을 조회한다.")
    @Test
    void findByCrew() {
        ReservationReserveRequest reserveRequest = new ReservationReserveRequest(crew.getId(), coach.getId(),
                schedule.getId());
        reservationService.save(reserveRequest);

        List<CrewHistoryFindResponse> reservations = reservationService.findByCrew(crew.getId());

        assertThat(reservations).hasSize(1);
    }
}
