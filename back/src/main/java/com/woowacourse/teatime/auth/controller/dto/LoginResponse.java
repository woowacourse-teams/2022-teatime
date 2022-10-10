package com.woowacourse.teatime.auth.controller.dto;

import com.woowacourse.teatime.teatime.domain.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private Role role;
    private String image;
    private String name;

    public static LoginResponse from(UserAuthDto userAuthDto) {
        return new LoginResponse(userAuthDto.getAccessToken(), userAuthDto.getRole(), userAuthDto.getImage(),
                userAuthDto.getName());
    }
}
