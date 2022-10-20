package com.woowacourse.teatime.auth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.teatime.auth.controller.dto.GenerateTokenDto;
import com.woowacourse.teatime.teatime.controller.ControllerTestSupporter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;


class AuthControllerTest extends ControllerTestSupporter {

    @DisplayName("토큰 재발급에 성공한다.")
    @Test
    void generateToken() throws Exception {
        //given
        String token = "나 크루다.";
        크루의_토큰을_검증한다(token);

        given(userAuthService.generateToken(any(), any()))
                .willReturn(new GenerateTokenDto("accessToken", "refreshToken"));

        //when
        ResultActions perform = mockMvc.perform(get("/api/auth/refresh-token")
                        .header("Authorization", "Bearer " + token))
                .andDo(print());

        //then
        perform.andExpect(status().isOk());
    }
}
