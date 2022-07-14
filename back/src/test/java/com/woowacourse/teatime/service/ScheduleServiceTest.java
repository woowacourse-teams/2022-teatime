package com.woowacourse.teatime.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.teatime.NotExistedCoachException;
import com.woowacourse.teatime.controller.dto.ScheduleRequest;
import com.woowacourse.teatime.controller.dto.ScheduleResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = AutowireMode.ALL)
class ScheduleServiceTest {

    @Autowired
    ScheduleService scheduleService;

    private static final ScheduleRequest REQUEST = new ScheduleRequest(2022, 7);

    @Test
    @DisplayName("코치의 한달 스케줄 목록을 조회한다.")
    void find() {
        List<ScheduleResponse> scheduleResponses = scheduleService.find(1L, REQUEST);

        assertThat(scheduleResponses).hasSize(2);
    }

    @Test
    @DisplayName("코치 아이디가 존재하지 않는 다면 예외를 발생시킨다,")
    void find_NotExistedCoachException() {
        Long notExistedId = 100L;

        assertThatThrownBy(() -> scheduleService.find(notExistedId, REQUEST))
                .isInstanceOf(NotExistedCoachException.class);
    }
}
