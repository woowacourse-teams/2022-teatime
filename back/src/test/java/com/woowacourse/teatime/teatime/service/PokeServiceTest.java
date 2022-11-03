package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCoachJason;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCrew;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.woowacourse.teatime.teatime.controller.dto.request.PokeSaveRequest;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Poke;
import com.woowacourse.teatime.teatime.exception.CannotPokeException;
import com.woowacourse.teatime.teatime.repository.PokeRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PokeServiceTest extends ServiceTestSupporter {

    @Autowired
    private PokeService pokeService;

    @Autowired
    private PokeRepository pokeRepository;

    @DisplayName("티타임을 요청한다.")
    @Test
    void save() {
        // given
        Crew crew = crewRepository.save(getCrew());
        Coach coach = coachRepository.save(getCoachJason());

        // when
        PokeSaveRequest request = new PokeSaveRequest(coach.getId());
        Long pokeId = pokeService.save(crew.getId(), request);

        // then
        Optional<Poke> actual = pokeRepository.findById(pokeId);
        assertTrue(actual.isPresent());
    }

    @DisplayName("티타임을 요청을 거부한 코치에게 티타임요청을 보내면 에러를 발생시킼다")
    @Test
    void save_false() {
        // given
        Crew crew = crewRepository.save(getCrew());
        Coach coach = coachRepository.save(getCoachJason());
        coach.modifyProfile(coach.getName(), coach.getDescription(), false);

        // when
        PokeSaveRequest request = new PokeSaveRequest(coach.getId());

        // then
        assertThatThrownBy(() -> pokeService.save(crew.getId(), request))
                .isInstanceOf(CannotPokeException.class);
    }
}
