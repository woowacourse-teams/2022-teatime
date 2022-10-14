package com.woowacourse.teatime.auth.service;

import static com.woowacourse.teatime.teatime.domain.Role.COACH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.teatime.auth.controller.dto.GenerateTokenDto;
import com.woowacourse.teatime.auth.domain.UserAuthInfo;
import com.woowacourse.teatime.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.teatime.auth.repository.UserAuthInfoRepository;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ExtendWith({MockitoExtension.class})
class UserAuthServiceTest {

    @Mock
    private UserAuthInfoRepository userAuthInfoRepository;

    @InjectMocks
    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String refreshToken;
    private String accessToken;
    private UserAuthInfo userAuthInfo;

    @BeforeEach
    void setUp() {
        refreshToken = UUID.randomUUID().toString();
        Map<String, Object> claims =
                Map.of("id", 1L, "role", COACH.name());
        accessToken = jwtTokenProvider.createToken(claims);
        userAuthInfo = new UserAuthInfo(refreshToken, accessToken, 1L, COACH.name());
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
