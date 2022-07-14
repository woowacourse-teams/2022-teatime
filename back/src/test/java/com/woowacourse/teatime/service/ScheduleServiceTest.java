package com.woowacourse.teatime.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.teatime.NotFoundCoachException;
import com.woowacourse.teatime.controller.dto.ScheduleRequest;
import com.woowacourse.teatime.controller.dto.ScheduleResponse;
import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.repository.CoachRepository;
import com.woowacourse.teatime.repository.ScheduleRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
class ScheduleServiceTest {

    private final ScheduleService scheduleService;
    private final CoachRepository coachRepository;
    private final ScheduleRepository scheduleRepository;

    private static final ScheduleRequest REQUEST = new ScheduleRequest(2022, 7);

    public ScheduleServiceTest(ScheduleService scheduleService,
                               CoachRepository coachRepository,
                               ScheduleRepository scheduleRepository) {
        this.scheduleService = scheduleService;
        this.coachRepository = coachRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Test
    @DisplayName("코치의 한달 스케줄 목록을 조회한다.")
    void find() {
        Coach coach = coachRepository.save(new Coach("brown"));
        scheduleRepository.save(new Schedule(coach, LocalDateTime.of(2022, 7, 1, 0, 0)));
        List<ScheduleResponse> scheduleResponses = scheduleService.find(coach.getId(), REQUEST);

        assertThat(scheduleResponses).hasSize(1);
    }

    @Test
    @DisplayName("코치 아이디가 존재하지 않는 다면 예외를 발생시킨다,")
    void find_NotExistedCoachException() {
        Coach coach = coachRepository.save(new Coach("brown"));
        Schedule schedule = scheduleRepository.save(new Schedule(coach, LocalDateTime.of(2022, 7, 1, 0, 0)));
        Long notExistedId = schedule.getId() + 100L;

        assertThatThrownBy(() -> scheduleService.find(notExistedId, REQUEST))
                .isInstanceOf(NotFoundCoachException.class);
    }
}
