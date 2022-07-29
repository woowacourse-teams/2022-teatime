package com.woowacourse.teatime.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewControllerTest extends ControllerTest {

    @DisplayName("크루의 면담 목록 조회에 성공한다.")
    @Test
    void findCrewReservations() throws Exception {
        mockMvc.perform(get("/api/crews/me/reservations", 1L))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("크루의 면담 목록 조회에 실패한다. - 잘못된 크루 아이디")
    @Test
    void findCrewReservationsFailWrongCrewId() throws Exception {
        mockMvc.perform(get("/api/crews/me/reservations", null))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
