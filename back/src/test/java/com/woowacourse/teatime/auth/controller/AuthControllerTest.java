package com.woowacourse.teatime.auth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.teatime.auth.controller.dto.GenerateTokenDto;
import com.woowacourse.teatime.teatime.controller.ControllerTestSupporter;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;


class AuthControllerTest extends ControllerTestSupporter {

    @DisplayName("토큰 재발급에 성공한다.")
    @Test
    void refreshToken() throws Exception {
        //given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        given(userAuthService.generateToken(any(), any()))
                .willReturn(new GenerateTokenDto("accessToken", "refreshToken"));

        //when
        ResultActions perform = mockMvc.perform(get("/api/auth/refresh-token")
                        .header("Authorization", "Bearer " + token)
                        .cookie(new Cookie("refreshToken", "refreshToken")))
                .andDo(print());

        //then
        perform.andExpect(status().isOk());
    }

    @DisplayName("로그아웃 시 토큰을 삭제한다.")
    @Test
    void logout() throws Exception {
        //given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        doNothing().when(userAuthService).deleteToken(any());

        //when
        ResultActions perform = mockMvc.perform(delete("/api/auth/logout")
                        .header("Authorization", "Bearer " + token)
                        .cookie(new Cookie("refreshToken", "refreshToken")))
                .andDo(print());

        //then
        perform.andExpect(status().isNoContent());
    }
}
