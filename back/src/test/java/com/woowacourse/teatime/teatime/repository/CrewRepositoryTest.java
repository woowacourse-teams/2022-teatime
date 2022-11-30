package com.woowacourse.teatime.teatime.repository;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.CREW1;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.support.RepositoryTestSupporter;
import com.woowacourse.teatime.teatime.domain.Crew;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewRepositoryTest extends RepositoryTestSupporter {

    @DisplayName("이메일로 크루를 조회한다.")
    @Test
    void findByEmail() {
        crewRepository.save(CREW1);

        Optional<Crew> crew = crewRepository.findByEmail(CREW1.getEmail());

        assertThat(crew).isNotEmpty();
    }

    @DisplayName("이메일로 크루를 조회한다. - 이메일이 존재하지 않는 경우.")
    @Test
    void findByEmail_notFound() {
        crewRepository.save(CREW1);

        Optional<Crew> crew = crewRepository.findByEmail("yaho@email.com");

        assertThat(crew).isEmpty();
    }
}
