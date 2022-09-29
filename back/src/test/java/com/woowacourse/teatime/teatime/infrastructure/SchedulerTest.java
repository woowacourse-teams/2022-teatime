package com.woowacourse.teatime.teatime.infrastructure;

import static com.woowacourse.teatime.teatime.domain.ReservationStatus.IN_PROGRESS;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCoachJason;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCrew;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.ReservationStatus;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import com.woowacourse.teatime.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.teatime.repository.ScheduleRepository;
import java.time.LocalDateTime;
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

    private Coach coach;
    private Crew crew;

    @BeforeEach
    void setUp() {
        coach = coachRepository.save(getCoachJason());
        crew = crewRepository.save(getCrew());
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

    @DisplayName("승인된 예약중 오늘 이전의 예약도 진행중인 예약으로 변경한다.")
    @Test
    void updateReservationStatusToInProgress_beforeToday() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);
        Schedule schedule = scheduleRepository.save(new Schedule(coach, yesterday));
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
}
