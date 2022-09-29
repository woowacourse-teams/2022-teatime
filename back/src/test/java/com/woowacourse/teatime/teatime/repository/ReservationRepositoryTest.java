package com.woowacourse.teatime.teatime.repository;

import static com.woowacourse.teatime.teatime.domain.ReservationStatus.BEFORE_APPROVED;
import static com.woowacourse.teatime.teatime.domain.ReservationStatus.CANCELED;
import static com.woowacourse.teatime.teatime.domain.ReservationStatus.DONE;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.LOCAL_DATE;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCoachJason;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.Role;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.fixture.DomainFixture;
import com.woowacourse.teatime.util.Date;
import java.time.LocalDateTime;
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
        crew = crewRepository.save(DomainFixture.getCrew());
        coach = coachRepository.save(DomainFixture.getCoachJason());
        schedule = scheduleRepository.save(new Schedule(coach, DomainFixture.DATE_TIME));
    }

    @DisplayName("면담을 생성할 때 updatedAt에 생성시간이 저장된다.")
    @Test
    void save() {
        LocalDateTime now = LocalDateTime.now();

        Reservation savedReservation = reservationRepository.save(new Reservation(schedule, crew));

        assertThat(savedReservation.getUpdatedAt()).isAfter(now);
    }

    @DisplayName("크루에 해당하는 면담 목록을 조회한다. - 면담 상태 : BEFORE_APPROVED")
    @Test
    void findAllByCrewIdAndReservationStatus() {
        reservationRepository.save(new Reservation(schedule, crew));
        reservationRepository.save(new Reservation(schedule, crew));
        reservationRepository.save(new Reservation(schedule, crew));

        List<Reservation> reservations =
                reservationRepository.findAllByCrewIdAndReservationStatus(crew.getId(),
                        BEFORE_APPROVED);

        assertThat(reservations).hasSize(3);
    }

    @DisplayName("코치의 한 달 면담 목록을 조회한다.")
    @Test
    void findByCoachIdExceptDoneAndCanceled() {
        Coach newCoach = coachRepository.save(DomainFixture.COACH_JASON);
        Schedule newCoachSchedule = scheduleRepository.save(new Schedule(newCoach, DomainFixture.DATE_TIME));
        Schedule schedule2 = scheduleRepository.save(new Schedule(coach, DomainFixture.DATE_TIME.plusDays(1)));

        reservationRepository.save(new Reservation(schedule, crew));
        reservationRepository.save(new Reservation(schedule2, crew));
        reservationRepository.save(new Reservation(newCoachSchedule, crew));

        List<Reservation> doneReservations = reservationRepository.findAllByCoachIdAndStatusNot(
                coach.getId(), DONE);
        List<Reservation> canceledReservations = reservationRepository.findAllByCoachIdAndStatusNot(
                coach.getId(), CANCELED);

        assertThat(List.of(doneReservations, canceledReservations)).hasSize(2);
    }

    @DisplayName("해당 시간 이전의 승인된 예약들을 모두 조회한다.")
    @Test
    void findAllApprovedReservationsBefore() {
        // given
        Coach jason = coachRepository.save(getCoachJason());
        Schedule jasonSchedule1 = scheduleRepository.save(new Schedule(jason, DomainFixture.DATE_TIME));
        Schedule brownSchedule2 = scheduleRepository.save(new Schedule(coach, DomainFixture.DATE_TIME.plusDays(1)));

        Reservation reservation1 = reservationRepository.save(new Reservation(jasonSchedule1, crew));
        Reservation reservation2 = reservationRepository.save(new Reservation(brownSchedule2, crew));
        reservationRepository.save(new Reservation(brownSchedule2, crew));

        reservation1.confirm();
        reservation2.confirm();

        // when
        List<Reservation> approvedReservations
                = reservationRepository.findAllApprovedReservationsBefore(Date.findLastTime(LOCAL_DATE));

        // then
        assertThat(approvedReservations).hasSize(1);
    }

    @DisplayName("코치에 해당하는 완료 상태의 면담 목록을 조회한다.")
    @Test
    void findAllByCoachIdAndStatus() {
        Reservation reservation1 = reservationRepository.save(new Reservation(schedule, crew));
        Reservation reservation2 = reservationRepository.save(new Reservation(schedule, crew));

        reservation1.confirm();
        reservation1.updateSheetStatusToSubmitted();
        reservation1.updateReservationStatusToInProgress();
        reservation1.updateReservationStatusToDone();

        reservation2.cancel(Role.COACH);

        assertThat(reservationRepository.findAllByCoachIdAndStatus(coach.getId(), DONE)).hasSize(1);
    }

    @DisplayName("해당 크루의 면담을 최신순으로 모두 가져온다.")
    @Test
    void findAllByCrewIdLatestOrder() {
        // given
        LocalDateTime dateTime1 = DomainFixture.DATE_TIME;
        LocalDateTime dateTime2 = DomainFixture.DATE_TIME.plusHours(1);
        Schedule schedule1 = scheduleRepository.save(new Schedule(coach, dateTime1));
        Schedule schedule2 = scheduleRepository.save(new Schedule(coach, dateTime2));
        Reservation reservation1 = reservationRepository.save(new Reservation(schedule1, crew));
        Reservation reservation2 = reservationRepository.save(new Reservation(schedule2, crew));

        // when
        List<Reservation> reservations = reservationRepository.findAllByCrewIdLatestOrder(crew.getId());

        // then
        assertAll(
                () -> assertThat(reservations).hasSize(2),
                () -> assertThat(reservations).containsExactly(reservation2, reservation1)
        );
    }


    @DisplayName("해당 시간 사이의 모든 승인된 예약들을 조회한다.")
    @Test
    void findAllApprovedReservationsBetween() {
        // given
        LocalDateTime dateTime1 = DomainFixture.DATE_TIME;
        LocalDateTime dateTime2 = DomainFixture.DATE_TIME.plusHours(1);
        Schedule schedule1 = scheduleRepository.save(new Schedule(coach, dateTime1));
        Schedule schedule2 = scheduleRepository.save(new Schedule(coach, dateTime2));
        Reservation reservation1 = reservationRepository.save(new Reservation(schedule1, crew));
        Reservation reservation2 = reservationRepository.save(new Reservation(schedule2, crew));
        reservation1.confirm();
        reservation2.confirm();

        // when
        List<Reservation> reservations = reservationRepository.findAllApprovedReservationsBetween(
                Date.findFirstTime(LOCAL_DATE),
                Date.findLastTime(LOCAL_DATE));

        // then
        assertThat(reservations).hasSize(2);
    }
}
