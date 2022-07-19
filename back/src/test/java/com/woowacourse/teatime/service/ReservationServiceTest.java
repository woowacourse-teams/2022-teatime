package com.woowacourse.teatime.service;

import static com.woowacourse.teatime.fixture.DomainFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Crew;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.exception.AlreadyReservedException;
import com.woowacourse.teatime.exception.NotExistedCrewException;
import com.woowacourse.teatime.exception.NotFoundScheduleException;
import com.woowacourse.teatime.repository.CoachRepository;
import com.woowacourse.teatime.repository.CrewRepository;
import com.woowacourse.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.repository.ScheduleRepository;

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
        Long reservationId = reservationService.save(crew.getId(), coach.getId(), schedule.getId());

        Optional<Reservation> actual = reservationRepository.findById(reservationId);
        assertTrue(actual.isPresent());
    }

    @DisplayName("예약을 할 때 크루 아이디가 존재하지 않는 아이디면 예외를 반환한다.")
    @Test
    void reserveFailNotExistedCrew() {
        assertThatThrownBy(() -> reservationService.save(crew.getId() + 100L, coach.getId(), schedule.getId()))
                .isInstanceOf(NotExistedCrewException.class);
    }

    @DisplayName("예약을 할 때 스케줄 아이디가 존재하지 않는 아이디면 예외를 반환한다.")
    @Test
    void reserveFailNotExistedSchedule() {
        assertThatThrownBy(() -> reservationService.save(crew.getId(), coach.getId(), schedule.getId() + 100L))
                .isInstanceOf(NotFoundScheduleException.class);
    }

    @DisplayName("예약을 할 때 코치 아이디가 일치하지 않으면 예외를 반환한다.")
    @Test
    void reserveFailNotMatchedCoach() {
        Coach fakeCoach = coachRepository.save(new Coach("ori"));

        assertThatThrownBy(() -> reservationService.save(crew.getId(), fakeCoach.getId(), schedule.getId()))
                .isInstanceOf(NotFoundScheduleException.class);
    }

    @DisplayName("예약을 할 때 예약할 수 없는 스케줄이면 예외를 반환한다.")
    @Test
    void reserveFailAlreadyReserved() {
        reservationService.save(crew.getId(), coach.getId(), schedule.getId());

        assertThatThrownBy(() -> reservationService.save(crew.getId(), coach.getId(), schedule.getId()))
                .isInstanceOf(AlreadyReservedException.class);
    }

    @DisplayName("예약을 승인한다.")
    @Test
    void approveReservation() {
        Long reservationId = reservationService.save(crew.getId(), coach.getId(), schedule.getId());

        reservationService.approve(coach.getId(), reservationId);

        Reservation foundReservation = reservationRepository.findById(reservationId).get();
        assertThat(foundReservation.isApproved()).isTrue();
    }
}
