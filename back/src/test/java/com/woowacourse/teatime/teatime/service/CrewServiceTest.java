package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCrew;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.support.ServiceTestSupporter;
import com.woowacourse.teatime.teatime.controller.dto.request.CrewUpdateProfileRequest;
import com.woowacourse.teatime.teatime.domain.Crew;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CrewServiceTest extends ServiceTestSupporter {

    @Autowired
    private CrewService crewService;

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
