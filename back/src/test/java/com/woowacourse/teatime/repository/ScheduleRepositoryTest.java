package com.woowacourse.teatime.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Schedule;
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

    @Test
    @DisplayName("스케줄 전체 목록을 조회한다.")
    void findAll() {
        LocalDateTime now = LocalDateTime.now();
        Coach coach = new Coach("제이슨");
        coachRepository.save(coach);
        scheduleRepository.save(new Schedule(coach, now));

        List<Schedule> schedules = this.scheduleRepository.findAll();

        assertThat(schedules).hasSize(1);
    }

    @Test
    @DisplayName("해당 코치와, 년, 월에 해당하는 스케줄 전체 목록을 조회한다.")
    void findByCoachIdAndLocalDateTimeBetween() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime dateTime1 = start.plusDays(1);
        LocalDateTime dateTime2 = start.plusDays(2);
        LocalDateTime end = start.plusDays(3);

        Coach coach = coachRepository.save(new Coach("제이슨"));
        Schedule schedule1 = new Schedule(coach, dateTime1);
        Schedule schedule2 = new Schedule(coach, dateTime2);
        scheduleRepository.save(schedule1);
        scheduleRepository.save(schedule2);

        List<Schedule> schedules = this.scheduleRepository.findByCoachIdAndLocalDateTimeBetweenOrderByLocalDateTime(
                coach.getId(), start, end);

        assertAll(
                () -> assertThat(schedules).hasSize(2),
                () -> assertThat(schedules.get(0)).isEqualTo(schedule1)
        );
    }

    @Test
    @DisplayName("해당 코치와, 날짜에 해당하는 하루 스케줄을 모두 삭제한다.")
    void deleteAllByCoachIdAndLocalDateTimeBetween() {
        LocalDateTime july1_1 = LocalDateTime.of(2022, 7, 1, 1, 0, 0);
        LocalDateTime july2_1 = LocalDateTime.of(2022, 7, 2, 1, 0, 0);

        Coach coach = coachRepository.save(new Coach("제이슨"));
        scheduleRepository.save(new Schedule(coach, july1_1));
        scheduleRepository.save(new Schedule(coach, july2_1));

        LocalDate localDate = LocalDate.of(2022, 7, 1);
        LocalDateTime start = Date.findFirstTime(localDate);
        LocalDateTime end = Date.findLastTime(localDate);
        scheduleRepository.deleteAllByCoachIdAndLocalDateTimeBetween(coach.getId(), start, end);

        List<Schedule> schedules = scheduleRepository.findAll();
        assertThat(schedules).hasSize(1);
    }
}
