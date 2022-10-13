package com.woowacourse.teatime.auth.service;

import static com.woowacourse.teatime.teatime.domain.Role.COACH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.teatime.auth.controller.dto.GenerateTokenDto;
import com.woowacourse.teatime.auth.domain.UserAuthInfo;
import com.woowacourse.teatime.auth.repository.UserAuthInfoRepository;
import com.woowacourse.teatime.auth.support.EmbeddedRedisConfig;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = EmbeddedRedisConfig.class)
@Transactional
class UserAuthServiceTest {

    @Autowired
    private UserAuthInfoRepository userAuthInfoRepository;

    @Autowired
    private UserAuthService userAuthService;

    @DisplayName("유저 인증 정보를 저장한다.")
    @Test
    void save() {
        String refreshToken = UUID.randomUUID().toString();
        UserAuthInfo userAuthInfo = new UserAuthInfo(refreshToken, "access-token", 1L, COACH.name());
        userAuthService.save(userAuthInfo);

        assertThat(userAuthInfoRepository.findById(refreshToken).isPresent()).isTrue();
    }

    @DisplayName("엑세스 토큰과 리프레시 토큰을 새로 생성한다.")
    @Test
    void generateToken() {
        String refreshToken = UUID.randomUUID().toString();
        UserAuthInfo userAuthInfo = new UserAuthInfo(refreshToken, "access-token", 1L, COACH.name());
        userAuthService.save(userAuthInfo);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        GenerateTokenDto generateTokenDto = userAuthService.generateToken(cookie, "access-token");

        Optional<UserAuthInfo> savedInfo = userAuthInfoRepository.findById(generateTokenDto.getRefreshToken());
        assertAll(
                () -> assertThat(userAuthInfoRepository.findById(refreshToken).isPresent()).isFalse(),
                () -> assertThat(savedInfo.isPresent()).isTrue(),
                () -> assertThat(savedInfo.get().getAccessToken()).isEqualTo(generateTokenDto.getAccessToken())
        );
    }
}
