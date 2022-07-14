package com.woowacourse.teatime.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.teatime.AlreadyExistedReservationException;
import com.woowacourse.teatime.NotExistedCrewException;
import com.woowacourse.teatime.NotExistedScheduleException;
import com.woowacourse.teatime.NotMatchedIdException;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.repository.ReservationRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = AutowireMode.ALL)
class ReservationServiceTest {

    private ReservationService reservationService;
    private ReservationRepository reservationRepository;

    public ReservationServiceTest(ReservationService reservationService,
                                  ReservationRepository reservationRepository) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
    }

    @DisplayName("예약을 한다.")
    @Test
    void reserve() {
        final Long crewId = 1L;
        final Long coachId = 1L;
        final Long scheduleId = 1L;
        final Reservation reservation = reservationService.save(crewId, coachId, scheduleId);

        final Optional<Reservation> actual = reservationRepository.findById(reservation.getId());
        assertThat(actual.get()).isNotNull();
    }

    @DisplayName("예약을 할 때 크루 아이디가 존재하지 않는 아이디면 예외를 반환한다.")
    @Test
    void reserveFailNotExistedCrew() {
        final Long crewId = 100L;
        final Long coachId = 1L;
        final Long scheduleId = 1L;

        assertThatThrownBy(() -> reservationService.save(crewId, coachId, scheduleId))
                .isInstanceOf(NotExistedCrewException.class);
    }

    @DisplayName("예약을 할 때 스케줄 아이디가 존재하지 않는 아이디면 예외를 반환한다.")
    @Test
    void reserveFailNotExistedSchedule() {
        final Long crewId = 1L;
        final Long coachId = 1L;
        final Long scheduleId = 100L;

        assertThatThrownBy(() -> reservationService.save(crewId, coachId, scheduleId))
                .isInstanceOf(NotExistedScheduleException.class);
    }

    @DisplayName("예약을 할 때 코치 아이디가 일치하지 않으면 예외를 반환한다.")
    @Test
    void reserveFailNotMatchedCoach() {
        final Long crewId = 1L;
        final Long coachId = 2L;
        final Long scheduleId = 1L;

        assertThatThrownBy(() -> reservationService.save(crewId, coachId, scheduleId))
                .isInstanceOf(NotMatchedIdException.class);
    }

    @DisplayName("예약을 할 때 예약할 수 없는 스케줄이면 예외를 반환한다.")
    @Test
    void reserveFailAlreadyReserved() {
        final Long crewId = 1L;
        final Long coachId = 1L;
        final Long scheduleId = 1L;

        reservationService.save(crewId, coachId, scheduleId);

        assertThatThrownBy(() -> reservationService.save(crewId, coachId, scheduleId))
                .isInstanceOf(AlreadyExistedReservationException.class);
    }
}
