package com.woowacourse.teatime.teatime.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CoachControllerTest extends ControllerTestSupporter {

    @DisplayName("크루가 코치 목록 조회에 성공한다.")
    @Test
    void findCoaches() throws Exception {
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        mockMvc.perform(get("/api/v2/coaches")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("토큰이 유효하지 않으면 크루가 코치 목록 조회에 실패한다.")
    @Test
    void findCoaches_unAuthorization() throws Exception {
        String token = "나 잘못된 토큰";

        mockMvc.perform(get("/api/v2/coaches")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("코치가 자신의 면담 현황 목록 조회에 성공한다.")
    @Test
    void findReservations() throws Exception {
        String token = "나 코치다.";
        코치의_토큰을_검증한다(token);

        mockMvc.perform(get("/api/v2/coaches/me/reservations")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("토큰이 유효하지 않으면 코치가 자신의 면담 현황 목록 조회에 살패한다.")
    @Test
    void findReservations_unAuthorization() throws Exception {
        String token = "나 잘못된 토큰";

        mockMvc.perform(get("/api/v2/coaches/me/reservations")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("코치가 자신의 면담 목록 조회에 성공한다.")
    @Test
    void coachFindOwnHistory() throws Exception {
        String token = "나 코치다.";
        코치의_토큰을_검증한다(token);

        mockMvc.perform(get("/api/v2/coaches/me/history")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("코치가 자신의 면담 목록 조회에 실패한다. - 잘못된 토큰")
    @Test
    void coachFindOwnHistory_invalidToken() throws Exception {
        String token = "나 잘못된 토큰.";

        mockMvc.perform(get("/api/v2/coaches/me/history")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("message").value("유효하지 않은 토큰입니다."));

    }
}
