package com.woowacourse.teatime.teatime.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.teatime.auth.support.dto.UserRoleDto;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationApproveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.teatime.exception.AlreadyApprovedException;
import com.woowacourse.teatime.teatime.exception.NotFoundReservationException;
import com.woowacourse.teatime.teatime.exception.NotFoundScheduleException;
import com.woowacourse.teatime.teatime.exception.SlackAlarmException;
import com.woowacourse.teatime.teatime.exception.UnableToCancelReservationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.web.servlet.ResultActions;

class ReservationControllerTest extends ControllerTestSupporter {

    @DisplayName("예약에 성공한다.")
    @Test
    void reserve() throws Exception {
        //given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(post("/api/v2/reservations", new ReservationReserveRequest(1L))
                .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isCreated());
    }

    @DisplayName("예약에 실패한다. - 잘못된 스케줄 아이디")
    @Test
    void reserveFail_invalidScheduleId() throws Exception {
        //given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(post("/api/v2/reservations", new ReservationReserveRequest(null))
                .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isBadRequest());
    }

    @DisplayName("예약에 실패한다. - 존재하지 않는 스케줄 아이디")
    @Test
    void reserveFail_notFoundScheduleId() throws Exception {
        //given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        doThrow(new NotFoundScheduleException()).when(reservationService)
                .save(anyLong(), any(ReservationReserveRequest.class));

        //when
        ResultActions perform = mockMvc.perform(post("/api/v2/reservations", new ReservationReserveRequest(1L))
                .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpectAll(
                status().isNotFound(),
                jsonPath("message").value("존재하지 않는 스케줄입니다.")
        );

        //docs
        perform.andDo(document("reserve-notFoundSchedule"));
    }

    @DisplayName("예약에 성공한다. - 슬렉 알람 예외가 발생했을 때, 로그만 남기고 예약은 성공시킨다.")
    @Test
    void reserve_slackException() throws Exception {
        //given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        doThrow(new SlackAlarmException()).when(reservationService)
                .save(anyLong(), any(ReservationReserveRequest.class));

        //when
        ResultActions perform = mockMvc.perform(post("/api/v2/reservations", new ReservationReserveRequest(1L))
                .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isOk());
    }

    @DisplayName("코치가 면담 승인에 성공한다.")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void approve(boolean isApproved) throws Exception {
        //given
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc
                .perform(post("/api/v2/reservations/1", new ReservationApproveRequest(isApproved))
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isOk());
    }

    @DisplayName("코치가 면담 승인에 실패한다. - 존재하지 않는 면담 아이디")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void approve_notFoundReservationId(boolean isApproved) throws Exception {
        //given
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        doThrow(new NotFoundReservationException()).when(reservationService)
                .approve(anyLong(), anyLong(), any(ReservationApproveRequest.class));

        //when
        ResultActions perform = mockMvc
                .perform(post("/api/v2/reservations/1", new ReservationApproveRequest(isApproved))
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpectAll(
                status().isNotFound(),
                jsonPath("message").value("존재하지 않는 예약입니다.")
        );

        //docs
        perform.andDo(document("reserve-approve-notFoundReservation"));
    }

    @DisplayName("코치가 면담 승인에 실패한다. - 이미 승인된 면담인 경우")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void approve_alreadyApprovedReservation(boolean isApproved) throws Exception {
        //given
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        doThrow(new AlreadyApprovedException()).when(reservationService)
                .approve(anyLong(), anyLong(), any(ReservationApproveRequest.class));

        //when
        ResultActions perform = mockMvc
                .perform(post("/api/v2/reservations/1", new ReservationApproveRequest(isApproved))
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("message").value("이미 승인이 되어 있습니다.")
        );

        //docs
        perform.andDo(document("reserve-approve-alreadyApproved"));
    }

    @DisplayName("예약 취소에 성공한다.")
    @Test
    void cancel() throws Exception {
        //given
        String token = "나 유저다";
        코치의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(delete("/api/v2/reservations/1")
                .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isNoContent());
    }

    @DisplayName("예약 취소에 실패한다.- 존재하지 않는 면담 아이디")
    @Test
    void cancel_notFoundReservation() throws Exception {
        //given
        String token = "나 유저다";
        코치의_토큰을_검증한다(token);

        doThrow(new NotFoundReservationException()).when(reservationService)
                .cancel(anyLong(), any(UserRoleDto.class));

        //when
        ResultActions perform = mockMvc.perform(delete("/api/v2/reservations/1")
                .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpectAll(
                status().isNotFound(),
                jsonPath("message").value("존재하지 않는 예약입니다.")
        );

        //docs
        perform.andDo(document("reserve-cancel-notFoundReservation"));
    }

    @DisplayName("예약 취소에 실패한다. -잘못된 면담 아이디")
    @Test
    void cancel_wrongReservationId() throws Exception {
        //given
        String token = "나 유저다";
        코치의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(delete("/api/v2/reservations/a")
                .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isBadRequest());
    }

    @DisplayName("예약 취소에 실패한다.- 크루가 진행중인 면담을 취소하는 경우")
    @Test
    void cancel_InProgressByCrew() throws Exception {
        //given
        String token = "나 크루다";
        크루의_토큰을_검증한다(token);

        doThrow(new UnableToCancelReservationException()).when(reservationService)
                .cancel(anyLong(), any(UserRoleDto.class));

        //when
        ResultActions perform = mockMvc.perform(delete("/api/v2/reservations/1")
                .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("message").value("예약을 취소할 수 없습니다.")
        );

        //docs
        perform.andDo(document("reserve-cancel-crewCancelInProgress"));
    }

    @DisplayName("코치가 진행중인 일정을 완료된 상태로 변경한다.")
    @Test
    void finish() throws Exception {
        //given
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(put("/api/v2/reservations/1")
                .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isNoContent());
    }

    @DisplayName("코치가 진행중인 일정을 완료된 상태로 변경하는데 실패한다. - 잘못된 면담 아이디")
    @Test
    void finish_wrongReservationId() throws Exception {
        //given
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(put("/api/v2/reservations/a")
                .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isBadRequest());
    }

    @DisplayName("코치가 진행중인 일정을 완료된 상태로 변경하는데 실패한다. - 존재하지 않는 면담 아이디")
    @Test
    void finish_notFoundReservationId() throws Exception {
        //given
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        doThrow(new NotFoundReservationException()).when(reservationService)
                .updateReservationStatusToDone(anyLong(), anyLong());

        //when
        ResultActions perform = mockMvc.perform(put("/api/v2/reservations/1")
                .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpectAll(
                status().isNotFound(),
                jsonPath("message").value("존재하지 않는 예약입니다.")
        );

        //docs
        perform.andDo(document("reserve-finish-notFoundReservation"));
    }
}
