package com.woowacourse.teatime.auth.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.auth.domain.UserAuthInfo;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserAuthInfoRepositoryTest {

    @Autowired
    UserAuthInfoRepository userAuthInfoRepository;

    @Test
    void save() {
        UserAuthInfo token = new UserAuthInfo("token", 1L, "0.0.0.0");
        userAuthInfoRepository.save(token);

        Optional<UserAuthInfo> saved = userAuthInfoRepository.findById("token");

        assertThat(saved.isPresent()).isTrue();
    }
}
