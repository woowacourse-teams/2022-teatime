package com.woowacourse.teatime.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.controller.dto.ScheduleRequest;
import com.woowacourse.teatime.controller.dto.ScheduleResponse;
import com.woowacourse.teatime.controller.dto.ScheduleUpdateRequest;
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

@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
class ScheduleServiceTest {

    @Autowired
    ScheduleService scheduleService;

    @Test
    @DisplayName("코치의 한달 스케줄 목록을 조회한다.")
    void find() {
        List<ScheduleResponse> scheduleResponses = scheduleService.find(1L, new ScheduleRequest(2022, 7));

        assertThat(scheduleResponses).hasSize(2);
    }

    @Test
    @DisplayName("코치의 날짜에 해당하는 하루 스케줄을 업데이트한다.")
    void update() {
        LocalDate date = LocalDate.of(2022, 7, 4);
        LocalDateTime localDateTime = LocalDateTime.of(date, LocalTime.MIN);
        ScheduleUpdateRequest request = new ScheduleUpdateRequest(date, List.of(localDateTime));
        scheduleService.update(1L, request);

        List<ScheduleResponse> scheduleResponses = scheduleService.find(1L, new ScheduleRequest(2022, 7));
        assertThat(scheduleResponses).hasSize(2);
    }
}
