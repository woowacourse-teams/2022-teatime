package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCoachJason;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCrew;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.COACH_JUNE_SAVE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.teatime.controller.dto.request.CoachSaveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.CoachUpdateProfileRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.CrewUpdateProfileRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindResponse;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CrewServiceTest {

    @Autowired
    private CrewService crewService;

    @Autowired
    private CrewRepository crewRepository;

    @DisplayName("자신의 이름을 수정한다.")
    @Test
    void updateProfile() {
        // given
        Crew crew = crewRepository.save(getCrew());

        // when
        String expected = "name";
        crewService.updateProfile(crew.getId(), new CrewUpdateProfileRequest(expected));

        // then
        assertThat(crew.getName()).isEqualTo(expected);
    }
}
