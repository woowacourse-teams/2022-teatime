package com.woowacourse.teatime.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.teatime.controller.dto.CrewIdRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationControllerTest extends ControllerTest {

    @DisplayName("예약에 성공한다.")
    @Test
    void reserve() throws Exception {
        mockMvc.perform(post("/api/coaches/1/schedules/1", new CrewIdRequest(1L)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("예약에 실패한다. - 잘못된 코치 아이디")
    @Test
    void reserveFailWrongCoachId() throws Exception {
        mockMvc.perform(post("/api/coaches/a/schedules/1", new CrewIdRequest(1L)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("예약에 실패한다. - 잘못된 스케줄 아이디")
    @Test
    void reserveFailWrongScheduleId() throws Exception {
        mockMvc.perform(post("/api/coaches/1/schedules/a", new CrewIdRequest(1L)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("예약에 실패한다. - 잘못된 크루 아이디")
    @Test
    void reserveFailWrongCrewId() throws Exception {
        mockMvc.perform(post("/api/coaches/1/schedules/1", new CrewIdRequest(null)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
