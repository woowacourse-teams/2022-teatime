package com.woowacourse.teatime.teatime.service;

import com.woowacourse.teatime.teatime.controller.dto.request.CoachUpdateProfileRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.CrewSaveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.CrewUpdateProfileRequest;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.exception.NotFoundCoachException;
import com.woowacourse.teatime.teatime.exception.NotFoundCrewException;
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

    public void updateProfile(Long crewId, CrewUpdateProfileRequest request) {
        Crew crew = crewRepository.findById(crewId)
                .orElseThrow(NotFoundCrewException::new);
        crew.modifyName(request.getName());
    }
}
