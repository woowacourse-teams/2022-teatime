package com.woowacourse.teatime.service;

import com.woowacourse.teatime.CoachResponse;
import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.CoachRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CoachService {

    private final CoachRepository coachRepository;

    public List<CoachResponse> findAll() {
        List<Coach> coaches = coachRepository.findAll();
        return coaches.stream()
                .map(CoachResponse::new)
                .collect(Collectors.toList());
    }
}
