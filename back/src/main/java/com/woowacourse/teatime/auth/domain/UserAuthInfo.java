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
@RedisHash(value = "refreshToken")
public class UserAuthInfo {

    @Id
    private String refreshToken;
    private Long userId;
    private String role;

    public boolean isSameInfo(String refreshToken, Long userId, String role) {
        return this.refreshToken.equals(refreshToken)
                && this.userId.equals(userId)
                && this.role.equals(role);
    }
}
