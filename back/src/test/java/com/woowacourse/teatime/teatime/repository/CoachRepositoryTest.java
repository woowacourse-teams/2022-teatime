package com.woowacourse.teatime.teatime.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.fixture.DomainFixture;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CoachRepositoryTest {

    @Autowired
    private CoachRepository coachRepository;

    @DisplayName("전체 코치 목록을 조회한다.")
    @Test
    void findAll_size3() {
        coachRepository.save(DomainFixture.COACH_BROWN);

        assertThat(coachRepository.findAll().size()).isEqualTo(1);
    }

    @DisplayName("전체 코치 목록을 조회한다.")
    @Test
    void findAll_size4() {
        coachRepository.save(DomainFixture.COACH_JASON);
        coachRepository.save(DomainFixture.COACH_BROWN);

        assertThat(coachRepository.findAll().size()).isEqualTo(2);
    }

    @DisplayName("이메일로 크루를 조회한다.")
    @Test
    void findByEmail() {
        coachRepository.save(DomainFixture.COACH_BROWN);

        Optional<Coach> coach = coachRepository.findByEmail(DomainFixture.COACH_BROWN.getEmail());

        assertThat(coach).isNotEmpty();
    }

    @DisplayName("이메일로 크루를 조회한다. - 이메일이 존재하지 않는 경우.")
    @Test
    void findByEmail_notFound() {
        coachRepository.save(DomainFixture.COACH_BROWN);

        Optional<Coach> coach = coachRepository.findByEmail(DomainFixture.COACH_JASON.getEmail());

        assertThat(coach).isEmpty();
    }
}
