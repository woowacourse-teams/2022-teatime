package com.woowacourse.teatime.auth.repository;

import static com.woowacourse.teatime.teatime.domain.Role.COACH;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.auth.domain.UserAuthInfo;
import com.woowacourse.teatime.auth.support.EmbeddedRedisConfig;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = EmbeddedRedisConfig.class)
class UserAuthInfoRepositoryTest {

    @Autowired
    private UserAuthInfoRepository userAuthInfoRepository;

    private final String refreshToken = UUID.randomUUID().toString();

    @BeforeEach
    void init() {
        UserAuthInfo token = new UserAuthInfo(refreshToken, "accessToken", 1L, COACH.name());
        userAuthInfoRepository.save(token);
    }

    @Test
    void save() {
        String refreshToken2 = UUID.randomUUID().toString();
        UserAuthInfo token = new UserAuthInfo(refreshToken2, "accessToken", 2L, COACH.name());
        userAuthInfoRepository.save(token);

        assertThat(userAuthInfoRepository.findById(refreshToken2).isPresent()).isTrue();
    }

    @Test
    void find() {
        Optional<UserAuthInfo> saved = userAuthInfoRepository.findById(refreshToken);

        assertThat(saved.isPresent()).isTrue();
    }
}
