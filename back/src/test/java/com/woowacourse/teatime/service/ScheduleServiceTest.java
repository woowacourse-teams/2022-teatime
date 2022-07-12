package com.woowacourse.teatime.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.controller.dto.ScheduleRequest;
import com.woowacourse.teatime.controller.dto.ScheduleResponse;
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
}
