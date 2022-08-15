package com.woowacourse.teatime.teatime.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.ReservationStatus;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.fixture.DomainFixture;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ReservationRepositoryTest {

    private Crew crew;
    private Coach coach;
    private Schedule schedule;

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
        crew = crewRepository.save(DomainFixture.CREW);
        coach = coachRepository.save(DomainFixture.COACH_BROWN);
        schedule = scheduleRepository.save(new Schedule(coach, DomainFixture.DATE_TIME));
    }

    @DisplayName("크루에 해당하는 면담 목록을 조회한다. - 면담 상태 : BEFORE_APPROVED")
    @Test
    void findByCrewId() {
        reservationRepository.save(new Reservation(schedule, crew));
        reservationRepository.save(new Reservation(schedule, crew));
        reservationRepository.save(new Reservation(schedule, crew));

        List<Reservation> reservations =
                reservationRepository.findByCrewIdAndReservationStatusOrderByScheduleLocalDateTimeDesc(crew.getId(),
                        ReservationStatus.BEFORE_APPROVED);

        assertThat(reservations).hasSize(3);
    }

    @DisplayName("코치의 한 달 면담 목록을 조회한다.")
    @Test
    void findByCoachIdExceptDone() {
        Coach newCoach = coachRepository.save(DomainFixture.COACH_JASON);
        Schedule newCoachSchedule = scheduleRepository.save(new Schedule(newCoach, DomainFixture.DATE_TIME));
        Schedule schedule2 = scheduleRepository.save(new Schedule(coach, DomainFixture.DATE_TIME.plusDays(1)));

        reservationRepository.save(new Reservation(schedule, crew));
        reservationRepository.save(new Reservation(schedule2, crew));
        reservationRepository.save(new Reservation(newCoachSchedule, crew));

        List<Reservation> reservations = reservationRepository.findByScheduleCoachIdAndReservationStatusNot(
                coach.getId(), ReservationStatus.DONE);

        assertThat(reservations).hasSize(2);
    }
}
