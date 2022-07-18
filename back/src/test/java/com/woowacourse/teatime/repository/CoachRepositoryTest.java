package com.woowacourse.teatime.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.domain.Coach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CoachRepositoryTest {

    @Autowired
    private CoachRepository coaches;

    @Test
    @DisplayName("전체 코치 목록을 조회한다.")
    void findAll_size3() {
        coaches.save(new Coach("제이슨"));

        assertThat(coaches.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("전체 코치 목록을 조회한다.")
    void findAll_size4() {
        coaches.save(new Coach("제이슨"));
        coaches.save(new Coach("브라운"));

        assertThat(coaches.findAll().size()).isEqualTo(2);
    }
}
