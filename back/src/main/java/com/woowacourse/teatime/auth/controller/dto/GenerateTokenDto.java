package com.woowacourse.teatime.auth.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenerateTokenDto {

    private String accessToken;
    private String refreshToken;
}
