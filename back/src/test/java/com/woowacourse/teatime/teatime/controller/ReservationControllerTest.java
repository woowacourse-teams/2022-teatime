package com.woowacourse.teatime.teatime.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationControllerTest extends ControllerTest {

    @DisplayName("예약에 성공한다.")
    @Test
    void reserve() throws Exception {
        mockMvc.perform(post("/api/reservations", new ReservationReserveRequest(1L, 1L, 1L)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("예약에 실패한다. - 잘못된 코치 아이디")
    @Test
    void reserveFailWrongCoachId() throws Exception {
        mockMvc.perform(post("/api/reservations", new ReservationReserveRequest(1L, null, 1L)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("예약에 실패한다. - 잘못된 스케줄 아이디")
    @Test
    void reserveFailWrongScheduleId() throws Exception {
        mockMvc.perform(post("/api/reservations", new ReservationReserveRequest(1L, 1L, null)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("예약에 실패한다. - 잘못된 크루 아이디")
    @Test
    void reserveFailWrongCrewId() throws Exception {
        mockMvc.perform(post("/api/reservations", new ReservationReserveRequest(null, 1L, 1L)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("예약 취소에 성공한다.")
    @ParameterizedTest
    @ValueSource(strings = {"CREW", "COACH"})
    void cancel(String role) throws Exception {
        mockMvc.perform(delete("/api/reservations/1")
                        .header("applicantId", 1L)
                        .header("role", role))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("예약 취소에 실패한다. -잘못된 신청자 아이디")
    @Test
    void cancelFailWrongApplicantId() throws Exception {
        mockMvc.perform(delete("/api/reservations/1")
                        .header("applicantId", "a")
                        .header("role", "CREW"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("진행중인 일정을 완료된 상태로 변경한다.")
    @Test
    void updateStatusToDone() throws Exception {
        mockMvc.perform(put("/api/reservations/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("진행중인 일정을 완료된 상태로 변경하는데 실패한다. -잘못된 면담 아이디")
    @Test
    void updateStatusToDone_wrongReservationId() throws Exception {
        mockMvc.perform(put("/api/reservations/a"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
