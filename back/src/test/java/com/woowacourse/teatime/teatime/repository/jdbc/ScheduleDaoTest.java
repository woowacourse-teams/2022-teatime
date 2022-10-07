package com.woowacourse.teatime.teatime.repository.jdbc;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.DATE_TIME;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCoachJason;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.ScheduleRepository;
import com.woowacourse.teatime.teatime.support.RepositoryTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class ScheduleDaoTest {

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @DisplayName("스케줄을 한번에 저장한다.")
    @Test
    void saveAll() {
        // given
        Coach coach = coachRepository.save(getCoachJason());

        // when
        scheduleDao.saveAll(List.of(new Schedule(coach, DATE_TIME),
                new Schedule(coach, DATE_TIME.plusMinutes(1)),
                new Schedule(coach, DATE_TIME.plusMinutes(2)),
                new Schedule(coach, DATE_TIME.plusMinutes(3)),
                new Schedule(coach, DATE_TIME.plusMinutes(4))));

        // then
        List<Schedule> schedules = scheduleRepository.findAll();
        assertThat(schedules).hasSize(5);
    }
}
