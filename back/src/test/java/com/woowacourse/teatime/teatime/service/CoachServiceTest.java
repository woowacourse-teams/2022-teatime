package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.COACH_JUNE_SAVE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.teatime.controller.dto.request.CoachSaveRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindResponse;
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
    private CoachService coachService;

    @DisplayName("코치 목록을 조회한다.")
    @Test
    void findAll() {
        CoachSaveRequest request1 = COACH_BROWN_SAVE_REQUEST;
        CoachSaveRequest request2 = COACH_JUNE_SAVE_REQUEST;
        coachService.save(request1);
        coachService.save(request2);
        List<CoachFindResponse> coaches = coachService.findAll();

        assertThat(coaches.size()).isEqualTo(2);
    }
}
