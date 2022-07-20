package com.woowacourse.teatime.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.controller.dto.CoachResponse;
import com.woowacourse.teatime.controller.dto.CoachSaveRequest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CoachServiceTest {

    @Autowired
    CoachService coachService;

    @Test
    @DisplayName("코치 목록을 조회한다.")
    void findAll() {
        CoachSaveRequest request1 = new CoachSaveRequest("brown", "i am legend", "image");
        CoachSaveRequest request2 = new CoachSaveRequest("june", "i am legend e", "image");
        coachService.save(request1);
        coachService.save(request2);
        List<CoachResponse> coaches = coachService.findAll();

        assertThat(coaches.size()).isEqualTo(2);
    }
}
