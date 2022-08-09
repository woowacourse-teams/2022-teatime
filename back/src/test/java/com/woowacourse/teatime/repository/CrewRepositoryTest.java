package com.woowacourse.teatime.repository;

import static com.woowacourse.teatime.fixture.DomainFixture.CREW;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.domain.Crew;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CrewRepositoryTest {

    @Autowired
    private CrewRepository crewRepository;

    @DisplayName("이메일로 크루를 조회한다.")
    @Test
    void findByEmail() {
        crewRepository.save(CREW);

        Optional<Crew> crew = crewRepository.findByEmail(CREW.getEmail());

        assertThat(crew).isNotEmpty();
    }

    @DisplayName("이메일로 크루를 조회한다. - 이메일이 존재하지 않는 경우.")
    @Test
    void findByEmail_notFound() {
        crewRepository.save(CREW);

        Optional<Crew> crew = crewRepository.findByEmail("yaho@email.com");

        assertThat(crew).isEmpty();
    }
}
