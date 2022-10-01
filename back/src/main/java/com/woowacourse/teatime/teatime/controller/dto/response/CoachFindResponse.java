package com.woowacourse.teatime.teatime.controller.dto.response;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.repository.dto.CoachWithPossible;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CoachFindResponse {

    private Long id;
    private String name;
    private String description;
    private String image;
    private Boolean isPossible;

    public CoachFindResponse(Coach coach, boolean isPossible) {
        this(coach.getId(), coach.getName(), coach.getDescription(), coach.getImage(), isPossible);
    }

    public static List<CoachFindResponse> of(List<CoachWithPossible> coachWithPossibles) {
        return coachWithPossibles.stream()
                .map(c -> new CoachFindResponse(c.getId(), c.getName(), c.getDescription(), c.getImage(),
                        c.getPossible()))
                .collect(Collectors.toList());
    }
}
