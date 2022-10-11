package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCoachJason;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCrew;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.woowacourse.teatime.teatime.controller.dto.request.PokeSaveRequest;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Poke;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import com.woowacourse.teatime.teatime.repository.PokeRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PokeServiceTest {

    @Autowired
    private PokeService pokeService;
    @MockBean
    private AlarmService alarmService;
    @Autowired
    private PokeRepository pokeRepository;
    @Autowired
    private CrewRepository crewRepository;
    @Autowired
    private CoachRepository coachRepository;

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
}
