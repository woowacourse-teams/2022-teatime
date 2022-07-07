package com.woowacourse.teatime.repository;

import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(schedules.size()).isOne();
    }
}
