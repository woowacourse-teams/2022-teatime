package com.woowacourse.teatime.teatime.infrastructure;

import static com.woowacourse.teatime.teatime.domain.ReservationStatus.APPROVED;
import static com.woowacourse.teatime.teatime.domain.ReservationStatus.IN_PROGRESS;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.DATE_TIME;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCoachJason;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCrew1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.ReservationStatus;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.repository.CanceledReservationRepository;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import com.woowacourse.teatime.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.teatime.repository.ScheduleRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class SchedulerTest {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CanceledReservationRepository canceledReservationRepository;

    private Coach coach;
    private Crew crew;

    @BeforeEach
    void setUp() {
        coach = coachRepository.save(getCoachJason());
        crew = crewRepository.save(getCrew1());
    }

    @DisplayName("승인된 예약을 진행중인 예약으로 변경한다.")
    @Test
    void updateReservationStatusToInProgress() {
        // given
        Schedule schedule = scheduleRepository.save(new Schedule(coach, LocalDateTime.now()));
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));
        reservation.confirm();

        // when
        scheduler.updateReservationStatusToInProgress();

        // then
        Reservation savedReservation = reservationRepository.findById(reservation.getId())
                .orElseThrow();
        ReservationStatus actual = savedReservation.getReservationStatus();
        assertThat(actual).isEqualTo(IN_PROGRESS);
    }

    @DisplayName("승인된 면담 중 전날까지 작성하지 않은 면담을 모두 취소한다.")
    @Test
    void cancelReservationNotSubmitted() {
        // given
        Schedule schedule1 = scheduleRepository.save(new Schedule(coach, DATE_TIME));
        Schedule schedule2 = scheduleRepository.save(new Schedule(coach, DATE_TIME.plusHours(1)));
        Reservation reservation1 = reservationRepository.save(new Reservation(schedule1, crew));
        Reservation reservation2 = reservationRepository.save(new Reservation(schedule2, crew));
        reservation1.confirm();
        reservation2.confirm();
        reservation1.updateSheetStatusToSubmitted();

        // when
        scheduler.cancelReservationNotSubmitted();

        // then
        assertAll(
                () -> assertThat(reservationRepository.findAllByCoachIdAndStatus(coach.getId(), APPROVED)).hasSize(1),
                () -> assertThat(canceledReservationRepository.findAllByCoachId(coach.getId())).hasSize(1)
        );
    }
}
