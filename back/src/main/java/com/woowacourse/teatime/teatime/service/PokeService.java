package com.woowacourse.teatime.teatime.service;

import com.woowacourse.teatime.teatime.controller.dto.request.PokeSaveRequest;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Poke;
import com.woowacourse.teatime.teatime.exception.NotFoundCoachException;
import com.woowacourse.teatime.teatime.exception.NotFoundCrewException;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import com.woowacourse.teatime.teatime.repository.PokeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PokeService {

    private final PokeAlarmService alarmService;
    private final PokeRepository pokeRepository;
    private final CoachRepository coachRepository;
    private final CrewRepository crewRepository;

    public Long save(Long crewId, PokeSaveRequest request) {
        Long coachId = request.getCoachId();
        Crew crew = crewRepository.findById(crewId)
                .orElseThrow(NotFoundCrewException::new);
        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(NotFoundCoachException::new);

        Poke savedPoke = pokeRepository.save(new Poke(crew, coach));
        alarmService.sendPoke(crew.getName(), coach.getSlackId());

        return savedPoke.getId();
    }
}
