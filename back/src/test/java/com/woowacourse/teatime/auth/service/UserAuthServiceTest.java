package com.woowacourse.teatime.auth.service;

import static com.woowacourse.teatime.teatime.domain.Role.COACH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import com.woowacourse.teatime.auth.controller.dto.GenerateTokenDto;
import com.woowacourse.teatime.auth.domain.UserAuthInfo;
import com.woowacourse.teatime.auth.infrastructure.JwtTokenProvider;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserAuthServiceTest {

    @Mock
    RedisTemplate<String, Object> template;

    @Mock
    ValueOperations<String, Object> valueOperations;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String refreshToken;
    private String accessToken;
    private UserAuthInfo userAuthInfo;

    @BeforeEach
    void setUp() {
        when(template.opsForValue()).thenReturn(valueOperations);

        refreshToken = UUID.randomUUID().toString();
        Map<String, Object> claims =
                Map.of("id", 1L, "role", COACH.name());
        accessToken = jwtTokenProvider.createToken(claims);
        userAuthInfo = new UserAuthInfo(refreshToken, accessToken, 1L, COACH.name());

        template.opsForValue().set(refreshToken, userAuthInfo);
    }

    @DisplayName("유저 인증 정보를 저장한다.")
    @Test
    void save() {
        assertDoesNotThrow(() -> userAuthService.save(userAuthInfo));
        assertThat(userAuthService.find(refreshToken)).isNotNull();
    }

    @DisplayName("엑세스 토큰과 리프레시 토큰을 새로 생성한다.")
    @Test
    void generateToken() {
        userAuthService.save(userAuthInfo);
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        GenerateTokenDto generateTokenDto = userAuthService.generateToken(cookie, accessToken);
        assertAll(
                () -> assertThat(generateTokenDto.getAccessToken()).isNotNull(),
                () -> assertThat(generateTokenDto.getRefreshToken()).isNotNull()
        );
    }
}
