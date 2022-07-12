package com.woowacourse.teatime.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Schedule;
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

        assertThat(schedules).hasSize(3);
    }

    @Test
    @DisplayName("해당 코치와, 년, 월에 해당하는 스케줄 전체 목록을 조회한다.")
    void findByCoachIdAndLocalDateTimeBetween() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime now1 = LocalDateTime.now();
        LocalDateTime now2 = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();

        Coach coach = new Coach("제이슨");
        coachRepository.save(coach);
        Schedule schedule1 = new Schedule(coach, now1);
        Schedule schedule2 = new Schedule(coach, now2);
        scheduleRepository.save(schedule1);
        scheduleRepository.save(schedule2);

        List<Schedule> schedules = this.scheduleRepository.findByCoachIdAndLocalDateTimeBetweenOrderByLocalDateTime(
                coach.getId(), start, end);

        assertAll(
                () -> assertThat(schedules).hasSize(2),
                () -> assertThat(schedules.get(0)).isEqualTo(schedule1)
        );
    }
}
