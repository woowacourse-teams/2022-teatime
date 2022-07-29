package com.woowacourse.teatime.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewControllerTest extends ControllerTest {

    @DisplayName("크루가 자신의 면담 목록 조회에 성공한다.")
    @Test
    void findOwnReservations() throws Exception {
        mockMvc.perform(get("/api/crews/me/reservations", 1L))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("크루가 자신의 면담 목록 조회에 실패한다. - 잘못된 크루 아이디")
    @Test
    void findOwnReservations_notFoundCrewId() throws Exception {
        mockMvc.perform(get("/api/crews/me/reservations", null))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("크루가 자신의 면담 중 하나의 시트 목록 조회에 성공한다.")
    @Test
    void findOwnSheets() throws Exception {
        mockMvc.perform(get("/api/crews/me/reservations/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("크루가 자신의 면담 중 하나의 시트 목록 조회에 실패한다. - 잘못된 면담 아이디")
    @Test
    void findOwnSheets_notFoundReservationId() throws Exception {
        mockMvc.perform(get("/api/crews/me/reservations/a"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
