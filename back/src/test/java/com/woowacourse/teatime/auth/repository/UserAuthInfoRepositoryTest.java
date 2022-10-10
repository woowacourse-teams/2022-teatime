package com.woowacourse.teatime.auth.repository;

import static com.woowacourse.teatime.teatime.domain.Role.COACH;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.auth.domain.UserAuthInfo;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserAuthInfoRepositoryTest {

    @Autowired
    UserAuthInfoRepository userAuthInfoRepository;

    @Test
    void save() {
        String refreshToken = UUID.randomUUID().toString();
        UserAuthInfo token = new UserAuthInfo(refreshToken, 1L, COACH.name());
        userAuthInfoRepository.save(token);

        Optional<UserAuthInfo> saved = userAuthInfoRepository.findById(refreshToken);

        assertThat(saved.isPresent()).isTrue();
    }
}
