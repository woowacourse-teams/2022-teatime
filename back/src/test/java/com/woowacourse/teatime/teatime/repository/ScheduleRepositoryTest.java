package com.woowacourse.teatime.teatime.repository;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCoachJason;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.fixture.DomainFixture;
import com.woowacourse.teatime.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CoachRepository coachRepository;

    @DisplayName("스케줄 전체 목록을 조회한다.")
    @Test
    void findAll() {
        LocalDateTime now = LocalDateTime.now();
        Coach coach = coachRepository.save(DomainFixture.COACH_JASON);
        scheduleRepository.save(new Schedule(coach, now));

        List<Schedule> schedules = scheduleRepository.findAll();

        assertThat(schedules).hasSize(1);
    }

    @DisplayName("해당 코치와, 년, 월에 해당하는 스케줄 전체 목록을 조회한다.")
    @Test
    void findAllByCoachIdBetween() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime dateTime1 = start.plusDays(1);
        LocalDateTime dateTime2 = start.plusDays(2);
        LocalDateTime end = start.plusDays(3);

        Coach coach = coachRepository.save(DomainFixture.COACH_JASON);
        Schedule schedule1 = new Schedule(coach, dateTime1);
        Schedule schedule2 = new Schedule(coach, dateTime2);
        scheduleRepository.save(schedule1);
        scheduleRepository.save(schedule2);

        List<Schedule> schedules = scheduleRepository.findAllByCoachIdBetween(
                coach.getId(), start, end);

        assertAll(
                () -> assertThat(schedules).hasSize(2),
                () -> assertThat(schedules.get(0)).isEqualTo(schedule1)
        );
    }

    @DisplayName("해당 코치와, 날짜에 해당하는 하루 스케줄을 모두 삭제한다.")
    @Test
    void deleteAllByCoachIdAndLocalDateTimeBetween() {
        // given
        LocalDateTime july1_1 = LocalDateTime.of(2022, 7, 1, 1, 0, 0);
        LocalDateTime july1_2 = LocalDateTime.of(2022, 7, 1, 2, 0, 0);

        Coach coach = coachRepository.save(DomainFixture.COACH_JASON);
        Schedule schedule1 = scheduleRepository.save(new Schedule(coach, july1_1));
        Schedule schedule2 = scheduleRepository.save(new Schedule(coach, july1_2));
        schedule2.reserve();

        // when
        LocalDate localDate = LocalDate.of(2022, 7, 1);
        LocalDateTime start = Date.findFirstTime(localDate);
        LocalDateTime end = Date.findLastTime(localDate);
        scheduleRepository.deleteAllReservableByCoachIdBetween(coach.getId(), start, end);

        // then
        List<Schedule> schedules = scheduleRepository.findAll();
        assertThat(schedules).hasSize(1);
    }

    @DisplayName("코치의 면담가능한 일정이 존재하는지 조회한다. - 가능한 일정이 있는 경우")
    @Test
    void existsIsPossibleByCoachId_true1() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTime1 = now.plusDays(1);
        LocalDateTime dateTime2 = now.plusDays(2);

        Coach coach = coachRepository.save(getCoachJason());
        Schedule schedule1 = scheduleRepository.save(new Schedule(coach, dateTime1));
        Schedule schedule2 = scheduleRepository.save(new Schedule(coach, dateTime2));
        schedule1.reserve();

        // when
        boolean actual = scheduleRepository.existsIsPossibleByCoachId(coach.getId());

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("코치의 면담가능한 일정이 존재하는지 조회한다. - 가능한 일정이 없는 경우")
    @Test
    void existsIsPossibleByCoachId_false() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTime1 = now.plusDays(1);
        LocalDateTime dateTime2 = now.plusDays(2);

        Coach coach = coachRepository.save(getCoachJason());
        Schedule schedule1 = scheduleRepository.save(new Schedule(coach, dateTime1));
        Schedule schedule2 = scheduleRepository.save(new Schedule(coach, dateTime2));
        schedule1.reserve();
        schedule2.reserve();

        // when
        boolean actual = scheduleRepository.existsIsPossibleByCoachId(coach.getId());

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("코치의 면담가능한 일정이 존재하는지 조회한다. - 등록된 일정이 하나도 없는 경우")
    @Test
    void existsIsPossibleByCoachId_false2() {
        // given
        Coach coach = coachRepository.save(getCoachJason());

        // when
        boolean actual = scheduleRepository.existsIsPossibleByCoachId(coach.getId());

        // then
        assertThat(actual).isFalse();
    }
}
