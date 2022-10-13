package com.woowacourse.teatime.auth.controller.dto;

import com.woowacourse.teatime.teatime.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAuthDto {

    private String accessToken;
    private String refreshToken;
    private Role role;
    private String image;
    private String name;
}
