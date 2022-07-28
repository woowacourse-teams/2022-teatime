package com.woowacourse.teatime.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.teatime.controller.dto.ReservationCancelRequest;
import com.woowacourse.teatime.controller.dto.ReservationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationControllerTest extends ControllerTest {

    @DisplayName("예약에 성공한다.")
    @Test
    void reserve() throws Exception {
        mockMvc.perform(post("/api/reservations", new ReservationRequest(1L, 1L, 1L)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("예약에 실패한다. - 잘못된 코치 아이디")
    @Test
    void reserveFailWrongCoachId() throws Exception {
        mockMvc.perform(post("/api/reservations", new ReservationRequest(1L, null, 1L)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("예약에 실패한다. - 잘못된 스케줄 아이디")
    @Test
    void reserveFailWrongScheduleId() throws Exception {
        mockMvc.perform(post("/api/reservations", new ReservationRequest(1L, 1L, null)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("예약에 실패한다. - 잘못된 크루 아이디")
    @Test
    void reserveFailWrongCrewId() throws Exception {
        mockMvc.perform(post("/api/reservations", new ReservationRequest(null, 1L, 1L)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("예약 취소에 성공한다.")
    @ParameterizedTest
    @ValueSource(strings = {"CREW", "COACH"})
    void cancel(String role) throws Exception {
        mockMvc.perform(delete("/api/reservations/1", new ReservationCancelRequest(1L, role)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("예약 취소에 실패한다. -잘못된 신청자 아이디")
    @ParameterizedTest
    @ValueSource(longs = {0, -2, -100})
    void cancelFailWrongApplicantId(Long applicantId) throws Exception {
        mockMvc.perform(delete("/api/reservations/1", new ReservationCancelRequest(applicantId, "CREW")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("예약 취소에 실패한다. -신청자 역할이 blank 인 경우")
    @ParameterizedTest
    @ValueSource(strings = {" ", "", "   "})
    void cancelFailWrongRole(String role) throws Exception {
        mockMvc.perform(delete("/api/reservations/1", new ReservationCancelRequest(1L, role)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
