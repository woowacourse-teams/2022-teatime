package com.woowacourse.teatime.teatime.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.teatime.auth.support.dto.UserRoleDto;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationApproveRequestV2;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequestV2;
import com.woowacourse.teatime.teatime.exception.AlreadyApprovedException;
import com.woowacourse.teatime.teatime.exception.NotFoundReservationException;
import com.woowacourse.teatime.teatime.exception.NotFoundScheduleException;
import com.woowacourse.teatime.teatime.exception.UnCancellableReservationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationControllerTest extends ControllerTest {

    @DisplayName("예약에 성공한다.")
    @Test
    void reserve() throws Exception {
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        mockMvc.perform(post("/api/v2/reservations", new ReservationReserveRequestV2(1L))
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("예약에 실패한다. - 잘못된 스케줄 아이디")
    @Test
    void reserveFail_invalidScheduleId() throws Exception {
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        mockMvc.perform(post("/api/v2/reservations", new ReservationReserveRequestV2(null))
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("예약에 실패한다. - 존재하지 않는 스케줄 아이디")
    @Test
    void reserveFail_notFoundScheduleId() throws Exception {
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        doThrow(new NotFoundScheduleException()).when(reservationService)
                .save(anyLong(), any(ReservationReserveRequestV2.class));

        mockMvc.perform(post("/api/v2/reservations", new ReservationReserveRequestV2(1L))
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("존재하지 않는 스케줄입니다."));
    }

    @DisplayName("코치가 면담 승인에 성공한다.")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void approve(boolean isApproved) throws Exception {
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        mockMvc.perform(post("/api/v2/reservations/1", new ReservationApproveRequestV2(isApproved))
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("코치가 면담 승인에 실패한다. - 존재하지 않는 면담 아이디")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void approve_notFoundReservationId(boolean isApproved) throws Exception {
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        doThrow(new NotFoundReservationException()).when(reservationService)
                .approve(anyLong(), anyLong(), any(ReservationApproveRequestV2.class));

        mockMvc.perform(post("/api/v2/reservations/1", new ReservationApproveRequestV2(isApproved))
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("존재하지 않는 예약입니다."));
    }

    @DisplayName("코치가 면담 승인에 실패한다. - 이미 승인된 면담인 경우")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void approve_alreadyApprovedReservation(boolean isApproved) throws Exception {
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        doThrow(new AlreadyApprovedException()).when(reservationService)
                .approve(anyLong(), anyLong(), any(ReservationApproveRequestV2.class));

        mockMvc.perform(post("/api/v2/reservations/1", new ReservationApproveRequestV2(isApproved))
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("이미 승인이 되어 있습니다."));
    }

    @DisplayName("예약 취소에 성공한다.")
    @Test
    void cancel() throws Exception {
        String token = "나 유저다";
        코치의_토큰을_검증한다(token);

        mockMvc.perform(delete("/api/v2/reservations/1")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("예약 취소에 실패한다.- 존재하지 않는 면담 아이디")
    @Test
    void cancel_notFoundReservation() throws Exception {
        String token = "나 유저다";
        코치의_토큰을_검증한다(token);

        doThrow(new NotFoundReservationException()).when(reservationService)
                .cancel(anyLong(), any(UserRoleDto.class));

        mockMvc.perform(delete("/api/v2/reservations/1")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("존재하지 않는 예약입니다."));
    }

    @DisplayName("예약 취소에 실패한다. -잘못된 면담 아이디")
    @Test
    void cancel_wrongReservationId() throws Exception {
        String token = "나 유저다";
        코치의_토큰을_검증한다(token);

        mockMvc.perform(delete("/api/v2/reservations/a")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("예약 취소에 실패한다.- 크루가 진행중인 면담을 취소하는 경우")
    @Test
    void cancel_InProgressByCrew() throws Exception {
        String token = "나 크루다";
        크루의_토큰을_검증한다(token);

        doThrow(new UnCancellableReservationException()).when(reservationService)
                .cancel(anyLong(), any(UserRoleDto.class));

        mockMvc.perform(delete("/api/v2/reservations/1")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("예약을 취소할 수 없습니다."));
    }

    @DisplayName("코치가 진행중인 일정을 완료된 상태로 변경한다.")
    @Test
    void finish() throws Exception {
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        mockMvc.perform(put("/api/v2/reservations/1")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("코치가 진행중인 일정을 완료된 상태로 변경하는데 실패한다. - 잘못된 면담 아이디")
    @Test
    void finish_wrongReservationId() throws Exception {
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        mockMvc.perform(put("/api/v2/reservations/a")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("코치가 진행중인 일정을 완료된 상태로 변경하는데 실패한다. - 존재하지 않는 면담 아이디")
    @Test
    void finish_notFoundReservationId() throws Exception {
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        doThrow(new NotFoundReservationException()).when(reservationService)
                .updateReservationStatusToDoneV2(anyLong(), anyLong());

        mockMvc.perform(put("/api/v2/reservations/1")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("존재하지 않는 예약입니다."));
    }
}
