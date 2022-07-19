package com.woowacourse.teatime.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.teatime.domain.Crew;
import com.woowacourse.teatime.repository.CrewRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CrewService {

    private final CrewRepository crewRepository;

    public Long save() {
        Crew crew = crewRepository.save(new Crew());
        return crew.getId();
    }
}
