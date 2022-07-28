package com.woowacourse.teatime.service;

import com.woowacourse.teatime.controller.dto.CoachResponse;
import com.woowacourse.teatime.controller.dto.CoachSaveRequest;
import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.repository.CoachRepository;
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
    public List<CoachResponse> findAll() {
        List<Coach> coaches = coachRepository.findAll();
        return coaches.stream()
                .map(CoachResponse::new)
                .collect(Collectors.toList());
    }

    public Long save(CoachSaveRequest request) {
        Coach coach = new Coach(request.getName(), request.getDescription(), request.getImage());
        return coachRepository.save(coach).getId();
    }
}
