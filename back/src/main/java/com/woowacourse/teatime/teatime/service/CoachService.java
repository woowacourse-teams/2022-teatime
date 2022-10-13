package com.woowacourse.teatime.teatime.service;

import com.woowacourse.teatime.teatime.controller.dto.request.CoachSaveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.CoachUpdateProfileRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachProfileResponse;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.exception.NotFoundCoachException;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.ScheduleRepository;
import com.woowacourse.teatime.teatime.repository.dto.CoachWithPossible;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CoachService {

    private final CoachRepository coachRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional(readOnly = true)
    public List<CoachFindResponse> findAll() {
        List<CoachWithPossible> coachWithPossibles = coachRepository.findCoaches();
        return CoachFindResponse.of(coachWithPossibles);
    }

    public Long save(CoachSaveRequest request) {
        Coach coach = new Coach(request.getSlackId(),
                request.getName(),
                request.getEmail(),
                request.getDescription(),
                request.getImage(),
                request.getIsPokable());
        return coachRepository.save(coach).getId();
    }

    public void updateProfile(Long coachId, CoachUpdateProfileRequest request) {
        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(NotFoundCoachException::new);
        coach.modifyProfile(request.getName(), request.getDescription(), request.getIsPokable());
    }

    public CoachProfileResponse getProfile(Long coachId) {
        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(NotFoundCoachException::new);
        return new CoachProfileResponse(coach);
    }
}
