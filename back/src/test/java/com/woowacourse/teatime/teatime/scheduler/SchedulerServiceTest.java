package com.woowacourse.teatime.teatime.scheduler;

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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class SchedulerServiceTest {

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @DisplayName("승인된 예약을 진행중인 예약으로 변경한다.")
    @Test
    void updateReservationStatusToInProgress() {
        // given
        Coach coach = coachRepository.save(getCoachJason());
        Crew crew = crewRepository.save(getCrew());
        Schedule schedule = scheduleRepository.save(new Schedule(coach, LocalDateTime.now()));
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));
        reservation.confirm(true);

        // when
        schedulerService.updateReservationStatusToInProgress();

        // then
        Reservation savedReservation = reservationRepository.findById(reservation.getId())
                .orElseThrow();
        ReservationStatus actual = savedReservation.getReservationStatus();
        assertThat(actual).isEqualTo(ReservationStatus.IN_PROGRESS);
    }
}
