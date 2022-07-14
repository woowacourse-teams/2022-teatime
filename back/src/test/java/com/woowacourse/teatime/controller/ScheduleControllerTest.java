package com.woowacourse.teatime.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.teatime.service.ScheduleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

class ScheduleControllerTest extends ControllerTest {

    @MockBean
    protected ScheduleService scheduleService;

    @DisplayName("코치 스케줄 조회에 성공한다.")
    @Test
    void findCoachSchedule() throws Exception {
        mockMvc.perform(get("/api/coaches/1/schedules?year=2022&month=07"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("코치 스케줄 조회에 실패한다 - 잘못된 코치 아이디")
    @Test
    void findCoachScheduleFailWrongCoachId() throws Exception {
        mockMvc.perform(get("/api/coaches/a/schedules?year=2022&month=07"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("코치 스케줄 조회에 실패한다 - 유효하지 않은 년도")
    @Test
    void findCoachScheduleFailWrongYear() throws Exception {
        mockMvc.perform(get("/api/coaches/1/schedules?year=2002&month=07"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("코치 스케줄 조회에 실패한다 - 유효하지 않은 월")
    @Test
    void findCoachScheduleFailWrongMonth() throws Exception {
        mockMvc.perform(get("/api/coaches/1/schedules?year=2022&month=13"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
