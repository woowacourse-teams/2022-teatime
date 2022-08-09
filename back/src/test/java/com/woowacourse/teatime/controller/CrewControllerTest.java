package com.woowacourse.teatime.controller;

import static com.woowacourse.teatime.domain.SheetStatus.SUBMITTED;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.teatime.controller.dto.request.SheetAnswerUpdateDto;
import com.woowacourse.teatime.controller.dto.request.SheetAnswerUpdateRequest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewControllerTest extends ControllerTest {

    @DisplayName("크루가 자신의 면담 목록 조회에 성공한다.")
    @Test
    void crewFindOwnReservations() throws Exception {
        mockMvc.perform(get("/api/crews/me/reservations").header("crewId", 1L))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("크루가 자신의 면담 목록 조회에 실패한다. - 잘못된 크루 아이디")
    @Test
    void crewFindOwnReservations_invalidCrewId() throws Exception {
        mockMvc.perform(get("/api/crews/me/reservations").header("crewId", "null"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("코치가 크루의 면담 목록 조회에 성공한다.")
    @Test
    void coachFindCrewReservations() throws Exception {
        mockMvc.perform(get("/api/crews/1/reservations"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("코치가 크루의 면담 목록 조회에 실패한다. - 잘못된 크루 아이디")
    @Test
    void coachFindCrewReservations_invalidReservationId() throws Exception {
        mockMvc.perform(get("/api/crews/a/reservations"))
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

    @DisplayName("크루가 자신의 면담 중 하나의 시트 목록 조회에 성공한다.")
    @Test
    void findCrewSheets() throws Exception {
        mockMvc.perform(get("/api/crews/me/reservations/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("크루가 자신의 면담 중 하나의 시트 목록 조회에 실패한다. - 잘못된 면담 아이디")
    @Test
    void findCrewSheets_notFoundReservationId() throws Exception {
        mockMvc.perform(get("/api/crews/1/reservations/a"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("크루가 자신의 면담 중 하나의 시트 목록 조회에 실패한다. - 잘못된 크루 아이디")
    @Test
    void findCrewSheets_notFoundCrewId() throws Exception {
        mockMvc.perform(get("/api/crews/a/reservations/1"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("크루가 자신의 면담 시트를 수정한다.")
    @Test
    void updateAnswer() throws Exception {
        mockMvc.perform(put("/api/crews/me/reservations/1",
                        new SheetAnswerUpdateRequest(SUBMITTED, List.of(new SheetAnswerUpdateDto(1, "a")))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("크루가 자신의 면담 시트를 수정에 실패한다. - 잘못된 면담 아이디")
    @Test
    void updateAnswer_notFoundReservationId() throws Exception {
        mockMvc.perform(put("/api/crews/me/reservations/a",
                        new SheetAnswerUpdateRequest(SUBMITTED, List.of(new SheetAnswerUpdateDto(1, "a")))))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("크루가 자신의 면담 시트를 수정에 실패한다 - 상태가 null인 경우")
    @Test
    void updateAnswer_statusNull() throws Exception {
        mockMvc.perform(put("/api/crews/me/reservations/a",
                        new SheetAnswerUpdateRequest(null, List.of(new SheetAnswerUpdateDto(1, "a")))))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("크루가 자신의 면담 시트를 수정에 실패한다 - 질문 번호가 null인 경우")
    @Test
    void updateAnswer_questionNumberNull() throws Exception {
        mockMvc.perform(put("/api/crews/me/reservations/a",
                        new SheetAnswerUpdateRequest(SUBMITTED, List.of(new SheetAnswerUpdateDto(null, "a")))))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
