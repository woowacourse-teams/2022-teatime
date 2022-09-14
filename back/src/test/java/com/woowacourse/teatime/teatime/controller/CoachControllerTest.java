package com.woowacourse.teatime.teatime.controller;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.COACH_BROWN;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.COACH_JASON;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.RESERVATION1;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.RESERVATION2;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.RESERVATION3;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindOwnHistoryResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachReservationsResponse;
import com.woowacourse.teatime.teatime.domain.Reservation;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.web.servlet.ResultActions;

class CoachControllerTest extends ControllerTestSupporter {

    @DisplayName("크루가 코치 목록 조회에 성공한다.")
    @Test
    void findCoaches() throws Exception {
        //given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        given(coachService.findAll())
                .willReturn(List.of(
                        new CoachFindResponse(COACH_BROWN),
                        new CoachFindResponse(COACH_JASON)
                ));

        //when
        ResultActions perform = mockMvc.perform(get("/api/v2/coaches")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isOk());
    }

    @DisplayName("토큰이 유효하지 않으면 크루가 코치 목록 조회에 실패한다.")
    @Test
    void findCoaches_unAuthorization() throws Exception {
        // given
        String token = "나 잘못된 토큰";

        //when
        ResultActions perform = mockMvc.perform(get("/api/v2/coaches")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpectAll(
                status().isUnauthorized(),
                jsonPath("message").value("유효하지 않은 토큰입니다.")
        );

        //docs
        perform.andDo(document("find-all-coaches-tokenException"));
    }

    @DisplayName("코치가 자신의 면담 현황 목록 조회에 성공한다.")
    @Test
    void findReservations() throws Exception {
        // given
        String token = "나 코치다.";
        코치의_토큰을_검증한다(token);

        List<Reservation> reservations = List.of(RESERVATION1);

        given(reservationService.findByCoachId(anyLong()))
                .willReturn(CoachReservationsResponse.of(
                        List.of(RESERVATION1),
                        List.of(RESERVATION2),
                        List.of(RESERVATION3)
                ));

        // when
        ResultActions perform = mockMvc.perform(get("/api/v2/coaches/me/reservations")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isOk());
    }

    @DisplayName("토큰이 유효하지 않으면 코치가 자신의 면담 현황 목록 조회에 살패한다.")
    @Test
    void findReservations_unAuthorization() throws Exception {
        String token = "나 잘못된 토큰";

        ResultActions perform = mockMvc.perform(get("/api/v2/coaches/me/reservations")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());
        //then
        perform.andExpectAll(
                status().isUnauthorized(),
                jsonPath("message").value("유효하지 않은 토큰입니다.")
        );

        //docs
        perform.andDo(document("find-coach-reservations-tokenException"));
    }

    @DisplayName("코치가 자신의 면담 내역(히스토리) 조회에 성공한다.")
    @Test
    void coachFindOwnHistory() throws Exception {
        // given
        String token = "나 코치다.";
        코치의_토큰을_검증한다(token);

        given(reservationService.findOwnHistoryByCoach(anyLong()))
                .willReturn(CoachFindOwnHistoryResponse.from(
                        List.of(RESERVATION1, RESERVATION2, RESERVATION3))
                );

        //when
        ResultActions perform = mockMvc.perform(get("/api/v2/coaches/me/history")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isOk());
    }

    @DisplayName("코치가 자신의 면담 내역(히스토리) 조회에 실패한다. - 잘못된 토큰")
    @Test
    void coachFindOwnHistory_invalidToken() throws Exception {
        //given
        String token = "나 잘못된 토큰.";

        ResultActions perform = mockMvc.perform(get("/api/v2/coaches/me/history")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        //when, then
        perform.andExpectAll(
                status().isUnauthorized(),
                jsonPath("message").value("유효하지 않은 토큰입니다.")
        );

        //docs
        perform.andDo(document("find-own-history-tokenException"));
    }

    @DisplayName("코치가 자신의 유저 네임 수정에 성공한다.")
    @Test
    void updateProfile() throws Exception {
        // given
        String token = "나 코치다.";
        코치의_토큰을_검증한다(token);

        //when
        String name = "제이슨";
        ResultActions perform = mockMvc.perform(put("/api/v2/coaches/me/profile", name)
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isNoContent());
    }

    @DisplayName("코치가 자신의 유저 네임 수정에 실패한다. - 이름이 공백인 경우 400 에러가 난다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void updateProfile_invalidProfile(String name) throws Exception {
        // given
        String token = "나 코치다.";
        코치의_토큰을_검증한다(token);

        //when
        ResultActions perform = mockMvc.perform(put("/api/v2/coaches/me/profile", name)
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isBadRequest());
    }
}
