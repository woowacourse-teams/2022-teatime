package com.woowacourse.teatime.auth.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 1209600)
public class UserAuthInfo {

    @Id
    private String refreshToken;
    private String accessToken;
    private Long userId;
    private String role;

    public boolean isSameToken(String refreshToken, String accessToken) {
        return this.refreshToken.equals(refreshToken)
                && this.accessToken.equals(accessToken);
    }
}
