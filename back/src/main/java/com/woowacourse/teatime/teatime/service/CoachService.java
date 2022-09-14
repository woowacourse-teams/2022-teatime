package com.woowacourse.teatime.teatime.service;

import com.woowacourse.teatime.teatime.controller.dto.request.CoachSaveRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindResponse;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CoachService {

    private final CoachRepository coachRepository;

    @Transactional(readOnly = true)
    public List<CoachFindResponse> findAll() {
        List<Coach> coaches = coachRepository.findAll();
        return coaches.stream()
                .map(CoachFindResponse::new)
                .collect(Collectors.toList());
    }

    public Long save(CoachSaveRequest request) {
        Coach coach = new Coach(request.getSlackId(), request.getName(), request.getDescription(), request.getImage());
        return coachRepository.save(coach).getId();
    }
}
