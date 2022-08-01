package com.woowacourse.teatime.service;

import static com.woowacourse.teatime.fixture.DomainFixture.COACH_BROWN;
import static com.woowacourse.teatime.fixture.DomainFixture.DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.teatime.controller.dto.request.ScheduleFindRequest;
import com.woowacourse.teatime.controller.dto.request.ScheduleUpdateRequest;
import com.woowacourse.teatime.controller.dto.response.ScheduleFindResponse;
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

    private static final LocalDate NOW = LocalDate.now();
    private static final int YEAR = NOW.getYear();
    private static final int MONTH = NOW.getMonthValue();
    private static final ScheduleFindRequest REQUEST = new ScheduleFindRequest(YEAR, MONTH);

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @DisplayName("코치의 오늘 이후 한달 스케줄 목록을 조회한다.")
    @Test
    void find_past() {
        Coach coach = coachRepository.save(COACH_BROWN);
        scheduleRepository.save(new Schedule(coach, LocalDateTime.now().minusDays(1L)));
        List<ScheduleFindResponse> responses = scheduleService.find(coach.getId(), REQUEST);

        assertThat(responses).hasSize(0);
    }

    @DisplayName("코치의 오늘 이후 한달 스케줄 목록을 조회한다.")
    @Test
    void find_future() {
        Coach coach = coachRepository.save(COACH_BROWN);
        scheduleRepository.save(new Schedule(coach, LocalDateTime.of(NOW, LocalTime.of(23, 59))));
        List<ScheduleFindResponse> responses = scheduleService.find(coach.getId(), REQUEST);

        assertThat(responses).hasSize(1);
    }

    @DisplayName("코치 아이디가 존재하지 않는 다면 예외를 발생시킨다,")
    @Test
    void find_NotExistedCoachException() {
        Coach coach = coachRepository.save(COACH_BROWN);
        Schedule schedule = scheduleRepository.save(new Schedule(coach, DATE_TIME));
        Long notExistedId = schedule.getId() + 100L;

        assertThatThrownBy(() -> scheduleService.find(notExistedId, REQUEST))
                .isInstanceOf(NotFoundCoachException.class);
    }

    @DisplayName("코치의 날짜에 해당하는 하루 스케줄을 업데이트한다.")
    @Test
    void update() {
        Coach coach = coachRepository.save(COACH_BROWN);
        LocalDate date = LocalDate.now();
        ScheduleUpdateRequest updateRequest = new ScheduleUpdateRequest(date, List.of(Date.findFirstTime(date)));
        scheduleService.update(coach.getId(), updateRequest);

        ScheduleFindRequest request = new ScheduleFindRequest(date.getYear(), date.getMonthValue());
        List<ScheduleFindResponse> responses = scheduleService.find(coach.getId(), request);
        assertThat(responses).hasSize(1);
    }
}
