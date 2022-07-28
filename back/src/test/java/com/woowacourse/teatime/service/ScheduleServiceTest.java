package com.woowacourse.teatime.service;

import static com.woowacourse.teatime.fixture.DomainFixture.COACH_BROWN;
import static com.woowacourse.teatime.fixture.DomainFixture.DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.teatime.controller.dto.ScheduleRequest;
import com.woowacourse.teatime.controller.dto.ScheduleResponse;
import com.woowacourse.teatime.controller.dto.ScheduleUpdateRequest;
import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Schedule;
import com.woowacourse.teatime.exception.NotFoundCoachException;
import com.woowacourse.teatime.repository.CoachRepository;
import com.woowacourse.teatime.repository.ScheduleRepository;
import com.woowacourse.teatime.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
class ScheduleServiceTest {

    private static final ScheduleRequest REQUEST = new ScheduleRequest(2022, 7);

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    @DisplayName("코치의 오늘 이후 한달 스케줄 목록을 조회한다.")
    void find_past() {
        Coach coach = coachRepository.save(COACH_BROWN);
        scheduleRepository.save(new Schedule(coach, LocalDateTime.now().minusDays(1L)));
        List<ScheduleResponse> scheduleResponses = scheduleService.find(coach.getId(), REQUEST);

        assertThat(scheduleResponses).hasSize(0);
    }

    @Test
    @DisplayName("코치의 오늘 이후 한달 스케줄 목록을 조회한다.")
    void find_future() {
        Coach coach = coachRepository.save(COACH_BROWN);
        scheduleRepository.save(new Schedule(coach, LocalDateTime.of(LocalDate.now(), LocalTime.MAX)));
        List<ScheduleResponse> scheduleResponses = scheduleService.find(coach.getId(), REQUEST);

        assertThat(scheduleResponses).hasSize(1);
    }

    @Test
    @DisplayName("코치 아이디가 존재하지 않는 다면 예외를 발생시킨다,")
    void find_NotExistedCoachException() {
        Coach coach = coachRepository.save(COACH_BROWN);
        Schedule schedule = scheduleRepository.save(new Schedule(coach, DATE_TIME));
        Long notExistedId = schedule.getId() + 100L;

        assertThatThrownBy(() -> scheduleService.find(notExistedId, REQUEST))
                .isInstanceOf(NotFoundCoachException.class);
    }

    @Test
    @DisplayName("코치의 날짜에 해당하는 하루 스케줄을 업데이트한다.")
    void update() {
        Coach coach = coachRepository.save(COACH_BROWN);
        LocalDate date = LocalDate.now();
        ScheduleUpdateRequest updateRequest = new ScheduleUpdateRequest(date, List.of(Date.findFirstTime(date)));
        scheduleService.update(coach.getId(), updateRequest);

        ScheduleRequest request = new ScheduleRequest(date.getYear(), date.getMonthValue());
        List<ScheduleResponse> scheduleResponses = scheduleService.find(coach.getId(), request);
        assertThat(scheduleResponses).hasSize(1);
    }
}
