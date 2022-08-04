package com.woowacourse.teatime.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.teatime.controller.dto.request.ScheduleUpdateRequest;
import com.woowacourse.teatime.exception.UnableToUpdateSchedule;
import com.woowacourse.teatime.util.Date;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScheduleControllerTest extends ControllerTest {

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

    @DisplayName("코치 스케줄 수정에 실패한다 - 스케줄이 예약되어 있는 상태이다")
    @Test
    void updateCoachScheduleFailReservedSchedule() throws Exception {
        given(scheduleService.update(any(Long.class), any(ScheduleUpdateRequest.class)))
                .willThrow(new UnableToUpdateSchedule());

        LocalDate date = LocalDate.now();
        mockMvc.perform(
                update("/api/coaches/1/schedules", new ScheduleUpdateRequest(date, List.of(Date.findLastTime(date)))))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message")
                        .value("일정을 수정할 수 없습니다."));

    }
}
