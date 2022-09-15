package com.woowacourse.teatime.teatime.controller;

import static com.woowacourse.teatime.teatime.domain.SheetStatus.SUBMITTED;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.SHEET_ANSWER_UPDATE_REQUEST_ONE;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.teatime.teatime.controller.dto.request.CrewUpdateProfileRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.SheetAnswerUpdateDto;
import com.woowacourse.teatime.teatime.controller.dto.request.SheetAnswerUpdateRequest;
import com.woowacourse.teatime.teatime.exception.NotFoundCrewException;
import com.woowacourse.teatime.teatime.exception.NotFoundReservationException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.web.servlet.ResultActions;

class CrewControllerTest extends ControllerTestSupporter {

    @DisplayName("크루가 자신의 면담 목록(히스토리) 조회에 성공한다.")
    @Test
    void crewFindOwnReservations() throws Exception {
        //given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(get("/api/v2/crews/me/reservations")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isOk());
    }

    @DisplayName("크루가 자신의 면담 목록(히스토리) 조회에 실패한다. - 잘못된 토큰")
    @Test
    void crewFindOwnReservations_invalidToken() throws Exception {
        //given
        String token = "나 잘못된 토큰.";

        //when
        ResultActions perform = mockMvc.perform(get("/api/v2/crews/me/reservations")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpectAll(
                status().isUnauthorized(),
                jsonPath("message").value("유효하지 않은 토큰입니다.")
        );
    }

    @DisplayName("코치가 크루의 면담 목록(히스토리) 조회에 성공한다.")
    @Test
    void coachFindCrewReservations() throws Exception {
        //given
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(get("/api/v2/crews/1/reservations")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isOk());
    }

    @DisplayName("코치가 크루의 면담 목록(히스토리) 조회에 실패한다. -  존재하지 않는 크루 아이디")
    @Test
    void coachFindCrewReservations_notFoundCrewId() throws Exception {
        //given
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        doThrow(new NotFoundCrewException()).when(reservationService)
                .findCrewHistoryByCoach(anyLong());

        //when
        ResultActions perform = mockMvc.perform(get("/api/v2/crews/1/reservations")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpectAll(
                status().isNotFound(),
                jsonPath("message").value("존재하지 않는 크루입니다.")
        );

        //docs
        perform.andDo(document("find-crew-sheets-notFoundCrew"));

    }


    @DisplayName("코치가 크루의 면담 목록(히스토리) 조회에 실패한다. - 잘못된 크루 아이디(숫자가 아닌 문자)")
    @Test
    void coachFindCrewReservations_invalidCrewId() throws Exception {
        //given
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(get("/api/v2/crews/a/reservations")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isBadRequest());
    }

    @DisplayName("크루가 자신의 면담 시트 목록 조회에 성공한다.")
    @Test
    void findOwnSheets() throws Exception {
        //given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(get("/api/v2/crews/me/reservations/1")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isOk());
    }

    @DisplayName("크루가 자신의 취소된 면담 시트 목록 조회에 성공한다.")
    @Test
    void findOwnCanceledSheets() throws Exception {
        //given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(get("/api/v2/crews/me/canceled-reservations/1")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isOk());
    }

    @DisplayName("크루가 자신의 면담 시트 목록 조회에 실패한다. - 존재하지 않는 면담 아이디")
    @Test
    void findOwnSheets_notFoundReservationId() throws Exception {
        //given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        doThrow(new NotFoundReservationException()).when(sheetService)
                .findOwnSheetByCrew(anyLong(), anyLong());

        //then
        ResultActions perform = mockMvc.perform(get("/api/v2/crews/me/reservations/1")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpectAll(
                status().isNotFound(),
                jsonPath("message").value("존재하지 않는 예약입니다.")
        );

        //docs
        perform.andDo(document("find-own-sheets-notFoundReservation"));
    }

    @DisplayName("크루가 자신의 면담 시트 목록 조회에 실패한다. - 잘못된 면담 아이디")
    @Test
    void findOwnSheets_invalidReservationId() throws Exception {
        //given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(get("/api/v2/crews/me/reservations/a")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isBadRequest());
    }

    @DisplayName("코치가 크루의 면담 시트 목록 조회에 성공한다.")
    @Test
    void findCrewSheets() throws Exception {
        //given
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(get("/api/v2/crews/1/reservations/1")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isOk());
    }

    @DisplayName("코치가 크루의 면담 시트 목록 조회에 실패한다. - 잘못된 면담 아이디")
    @Test
    void findCrewSheets_invalidReservationId() throws Exception {
        //given
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(get("/api/v2/crews/1/reservations/a")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isBadRequest());
    }

    @DisplayName("코치가 자신의 면담 시트 목록 조회에 실패한다. - 잘못된 크루 아이디")
    @Test
    void findCrewSheets_invalidCrewId() throws Exception {
        //given
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(get("/api/v2/crews/a/reservations/1")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isBadRequest());
    }

    @DisplayName("코치가 크루의 면담 시트 목록 조회에 실패한다. - 존재하지 않는 크루 아이디")
    @Test
    void findCrewSheets_notFoundCrewId() throws Exception {
        //given
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        doThrow(new NotFoundCrewException()).when(sheetService)
                .findCrewSheetByCoach(anyLong(), anyLong());

        //when
        ResultActions perform = mockMvc.perform(get("/api/v2/crews/1/reservations/1")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isNotFound());

        //then
        perform.andExpect(jsonPath("message").value("존재하지 않는 크루입니다."));

        //docs
        perform.andDo(document("find-crew-reservations-notFoundCrew"));
    }

    @DisplayName("코치가 크루의 면담 시트 목록 조회에 실패한다. - 존재하지 않는 면담 아이디")
    @Test
    void findCrewSheets_notFoundReservationId() throws Exception {
        //given
        String token = "나 코치다";
        코치의_토큰을_검증한다(token);

        doThrow(new NotFoundReservationException()).when(sheetService)
                .findCrewSheetByCoach(anyLong(), anyLong());

        //when
        ResultActions perform = mockMvc.perform(get("/api/v2/crews/1/reservations/1")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpectAll(
                status().isNotFound(),
                jsonPath("message").value("존재하지 않는 예약입니다.")
        );

        //docs
        perform.andDo(document("find-crew-reservations-notFoundReservation"));
    }

    @DisplayName("크루가 자신의 면담 시트를 수정한다.")
    @Test
    void updateAnswer() throws Exception {
        //given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(put("/api/v2/crews/me/reservations/1",
                        new SheetAnswerUpdateRequest(SUBMITTED, List.of(SHEET_ANSWER_UPDATE_REQUEST_ONE)))
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isOk());
    }

    @DisplayName("크루가 자신의 면담 시트 수정에 실패한다. - 잘못된 면담 아이디")
    @Test
    void updateAnswer_notFoundReservationId() throws Exception {
        //given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(put("/api/v2/crews/me/reservations/a",
                        new SheetAnswerUpdateRequest(SUBMITTED, List.of(SHEET_ANSWER_UPDATE_REQUEST_ONE)))
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isBadRequest());
    }

    @DisplayName("크루가 자신의 면담 시트 수정에 실패한다 - 상태가 null인 경우")
    @Test
    void updateAnswer_statusNull() throws Exception {
        //given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(put("/api/v2/crews/me/reservations/a",
                        new SheetAnswerUpdateRequest(null, List.of(SHEET_ANSWER_UPDATE_REQUEST_ONE)))
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isBadRequest());
    }

    @DisplayName("크루가 자신의 면담 시트 수정에 실패한다 - 질문 번호가 null인 경우")
    @Test
    void updateAnswer_questionNumberNull() throws Exception {
        //given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(put("/api/v2/crews/me/reservations/a",
                        new SheetAnswerUpdateRequest(SUBMITTED, List.of(new SheetAnswerUpdateDto(null, "a", "a"))))
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isBadRequest());
    }

    @DisplayName("크루가 자신의 유저 네임 수정에 성공한다.")
    @Test
    void updateProfile() throws Exception {
        // given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        //when
        CrewUpdateProfileRequest request = new CrewUpdateProfileRequest("쿄");
        ResultActions perform = mockMvc.perform(put("/api/v2/crews/me/profile", request)
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isNoContent());
    }

    @DisplayName("크루가 자신의 유저 네임 수정에 실패한다. - 이름이 공백인 경우 400 에러가 난다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void updateProfile_invalidProfile(String name) throws Exception {
        // given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(put("/api/v2/crews/me/profile", new CrewUpdateProfileRequest(name))
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isBadRequest());
    }
}
