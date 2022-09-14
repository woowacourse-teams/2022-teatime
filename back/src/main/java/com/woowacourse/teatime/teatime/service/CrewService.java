package com.woowacourse.teatime.teatime.service;

import com.woowacourse.teatime.teatime.controller.dto.request.CrewSaveRequest;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CrewService {

    private final CrewRepository crewRepository;

    public Long save(CrewSaveRequest crewSaveRequest) {
        Crew crew = new Crew(crewSaveRequest.getSlackId(), crewSaveRequest.getName(), crewSaveRequest.getEmail(),
                crewSaveRequest.getImage());
        return crewRepository.save(crew).getId();
    }
}
