package com.woowacourse.teatime.teatime.service;

import com.woowacourse.teatime.teatime.controller.dto.request.PokeSaveRequest;
import com.woowacourse.teatime.teatime.domain.Poke;
import com.woowacourse.teatime.teatime.repository.PokeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PokeService {

    private final PokeRepository pokeRepository;

    public Long save(Long crewId, PokeSaveRequest request) {
        Poke savedPoke = pokeRepository.save(new Poke(crewId, request.getCoachId()));
        return savedPoke.getId();
    }
}
