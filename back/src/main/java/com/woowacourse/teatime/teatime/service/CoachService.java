package com.woowacourse.teatime.teatime.service;

import com.woowacourse.teatime.teatime.controller.dto.request.CoachSaveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.CoachUpdateProfileRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachProfileResponse;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.exception.NotFoundCoachException;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.ScheduleRepository;
import java.util.LinkedList;
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
        List<CoachFindResponse> response = new LinkedList<>();
        List<Coach> coaches = coachRepository.findAll();
        for (Coach coach : coaches) {
            boolean isPossible = scheduleRepository.existsIsPossibleByCoachId(coach.getId());
            response.add(new CoachFindResponse(coach, isPossible));
        }
        return response;
    }

    public Long save(CoachSaveRequest request) {
        Coach coach = new Coach(request.getSlackId(), request.getName(), request.getDescription(), request.getImage());
        return coachRepository.save(coach).getId();
    }

    public void updateProfile(Long coachId, CoachUpdateProfileRequest request) {
        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(NotFoundCoachException::new);
        coach.modifyProfile(request.getName(), request.getDescription());
    }

    public CoachProfileResponse getProfile(Long coachId) {
        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(NotFoundCoachException::new);
        return new CoachProfileResponse(coach);
    }
}
