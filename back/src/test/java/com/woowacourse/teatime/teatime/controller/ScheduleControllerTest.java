package com.woowacourse.teatime.teatime.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.teatime.teatime.controller.dto.request.ScheduleFindRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ScheduleUpdateRequest;
import com.woowacourse.teatime.teatime.exception.NotFoundCoachException;
import com.woowacourse.teatime.teatime.exception.UnableToUpdateScheduleException;
import com.woowacourse.teatime.util.Date;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ScheduleControllerTest extends ControllerTest {

    @DisplayName("크루가 코치 스케줄 조회에 성공한다.")
    @Test
    void findCoachSchedules() throws Exception {
        String token = "나 크루다";
        크루의_토큰을_검증한다(token);

        mockMvc.perform(get("/api/v2/coaches/1/schedules?year=2022&month=07")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("크루가 코치 스케줄 조회에 실패한다 - 잘못된 코치 아이디")
    @Test
    void findCoachSchedules_wrongCoachId() throws Exception {
        String token = "나 크루다";
        크루의_토큰을_검증한다(token);

        mockMvc.perform(get("/api/v2/coaches/a/schedules?year=2022&month=07")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("크루가 코치 스케줄 조회에 실패한다 - 존재하지 않는 코치 아이디")
    @Test
    void findCoachSchedules_notFoundCoachId() throws Exception {
        String token = "나 크루다";
        크루의_토큰을_검증한다(token);

        doThrow(new NotFoundCoachException()).when(scheduleService)
                .find(anyLong(), any(ScheduleFindRequest.class));

        mockMvc.perform(get("/api/v2/coaches/1/schedules?year=2022&month=07")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("존재하지 않는 코치입니다."));
    }

    @DisplayName("크루가 코치 스케줄 조회에 실패한다 - 유효하지 않은 년도")
    @ParameterizedTest
    @ValueSource(strings = {"100", "1997", "2002", "2017"})
    void findCoachSchedules_wrongYear(int year) throws Exception {
        String token = "나 크루다";
        크루의_토큰을_검증한다(token);

        mockMvc.perform(get("/api/v2/coaches/1/schedules?year=" + year + "&month=07")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("크루가 코치 스케줄 조회에 실패한다 - 유효하지 않은 월")
    @ParameterizedTest
    @ValueSource(strings = {"13", "20", "30", "100"})
    void findCoachSchedules_wrongMonth(String month) throws Exception {
        String token = "나 크루다";
        크루의_토큰을_검증한다(token);

        mockMvc.perform(get("/api/v2/coaches/1/schedules?year=2022&month=" + month)
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("코치가 자신의 스케줄 조회에 성공한다.")
    @Test
    void findOwnSchedules() throws Exception {
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        mockMvc.perform(get("/api/v2/coaches/me/schedules?year=2022&month=07")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("코치가 스케줄 수정에 성공한다.")
    @Test
    void updateSchedules() throws Exception {
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        LocalDate date = LocalDate.now();
        mockMvc.perform(put("/api/v2/coaches/me/schedules",
                new ScheduleUpdateRequest(date, List.of(Date.findLastTime(date))))
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("코치가 코치 스케줄 수정에 실패한다 - 스케줄이 예약되어 있는 상태이다")
    @Test
    void updateCoachScheduleFailReservedSchedule() throws Exception {
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        doThrow(new UnableToUpdateScheduleException()).when(scheduleService)
                .update(anyLong(), any(ScheduleUpdateRequest.class));

        LocalDate date = LocalDate.now();
        mockMvc.perform(put("/api/v2/coaches/me/schedules",
                new ScheduleUpdateRequest(date, List.of(Date.findLastTime(date))))
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("일정을 수정할 수 없습니다."));
    }
}
