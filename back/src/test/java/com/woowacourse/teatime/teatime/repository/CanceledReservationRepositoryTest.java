package com.woowacourse.teatime.teatime.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.teatime.domain.CanceledReservation;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.fixture.DomainFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CanceledReservationRepositoryTest {

    private Crew crew;
    private Coach coach;
    private Schedule schedule;

    @Autowired
    private CanceledReservationRepository canceledReservationRepository;
    @Autowired
    private CrewRepository crewRepository;
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        crew = crewRepository.save(DomainFixture.CREW1);
        coach = coachRepository.save(DomainFixture.COACH_BROWN);
        schedule = scheduleRepository.save(new Schedule(coach, DomainFixture.DATE_TIME));
    }

    @DisplayName("코치의 취소된 예약 목록을 조회한다.")
    @Test
    void findAllByCoachId() {
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));
        canceledReservationRepository.save(CanceledReservation.from(reservation));

        assertThat(canceledReservationRepository.findAllByCoachId(coach.getId())).hasSize(1);
    }

    @DisplayName("크루의 취소된 예약 목록을 조회한다.")
    @Test
    void findAllByCrewId() {
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));
        canceledReservationRepository.save(CanceledReservation.from(reservation));

        assertThat(canceledReservationRepository.findAllByCrewId(crew.getId())).hasSize(1);
    }

    @DisplayName("기존의 예약 ID로 취소된 예약을 찾는다.")
    @Test
    void findByOriginId() {
        Reservation reservation = reservationRepository.save(new Reservation(schedule, crew));
        canceledReservationRepository.save(CanceledReservation.from(reservation));

        assertThat(canceledReservationRepository.findByOriginId(reservation.getId())).isNotEmpty();
    }
}
