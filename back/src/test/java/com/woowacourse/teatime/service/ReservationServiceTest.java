package com.woowacourse.teatime.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.teatime.AlreadyExistedReservationException;
import com.woowacourse.teatime.NotExistedCrewException;
import com.woowacourse.teatime.NotExistedScheduleException;
import com.woowacourse.teatime.NotMatchedIdException;
import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Crew;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.repository.CoachRepository;
import com.woowacourse.teatime.repository.CrewRepository;
import com.woowacourse.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.repository.ScheduleRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
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

    private Crew crew;
    private Coach coach;
    private Schedule schedule;

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;
    private final CrewRepository crewRepository;
    private final CoachRepository coachRepository;
    private final ScheduleRepository scheduleRepository;

    public ReservationServiceTest(ReservationService reservationService,
                                  ReservationRepository reservationRepository,
                                  CrewRepository crewRepository,
                                  CoachRepository coachRepository,
                                  ScheduleRepository scheduleRepository) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
        this.crewRepository = crewRepository;
        this.coachRepository = coachRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @BeforeEach
    void setUp() {
        crew = crewRepository.save(new Crew());
        coach = coachRepository.save(new Coach("jason"));
        schedule = scheduleRepository.save(new Schedule(coach, LocalDateTime.of(2022, 7, 1, 0, 0)));
    }

    @DisplayName("예약을 한다.")
    @Test
    void reserve() {
        final Reservation reservation = reservationService.save(crew.getId(), coach.getId(), schedule.getId());

        final Optional<Reservation> actual = reservationRepository.findById(reservation.getId());
        assertThat(actual.isPresent()).isTrue();
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
        assertThatThrownBy(() -> reservationService.save(crew.getId(), coach.getId(), schedule.getId() + +100L))
                .isInstanceOf(NotExistedScheduleException.class);
    }

    @DisplayName("예약을 할 때 코치 아이디가 일치하지 않으면 예외를 반환한다.")
    @Test
    void reserveFailNotMatchedCoach() {
        final Coach fakeCoach = coachRepository.save(new Coach("ori"));

        assertThatThrownBy(() -> reservationService.save(crew.getId(), fakeCoach.getId(), schedule.getId()))
                .isInstanceOf(NotMatchedIdException.class);
    }

    @DisplayName("예약을 할 때 예약할 수 없는 스케줄이면 예외를 반환한다.")
    @Test
    void reserveFailAlreadyReserved() {
        reservationService.save(crew.getId(), coach.getId(), schedule.getId());

        assertThatThrownBy(() -> reservationService.save(crew.getId(), coach.getId(), schedule.getId()))
                .isInstanceOf(AlreadyExistedReservationException.class);
    }
}
