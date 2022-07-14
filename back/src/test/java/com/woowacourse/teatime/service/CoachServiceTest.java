package com.woowacourse.teatime.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.controller.dto.CoachResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = AutowireMode.ALL)
public class CoachServiceTest {

    @Autowired
    CoachService coachService;

    @Test
    @DisplayName("코치 목록을 조회한다.")
    void findAll() {
        List<CoachResponse> coaches = coachService.findAll();

        assertThat(coaches.size()).isEqualTo(2);
    }
}
