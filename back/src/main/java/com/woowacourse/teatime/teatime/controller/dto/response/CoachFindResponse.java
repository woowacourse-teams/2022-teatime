package com.woowacourse.teatime.teatime.controller.dto.response;

import com.woowacourse.teatime.teatime.domain.Coach;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoachFindResponse {

    private Long id;
    private String name;
    private String description;
    private String image;
    private Boolean isPossible;

    public CoachFindResponse(Coach coach, boolean isPossible) {
        this(coach.getId(), coach.getName(), coach.getDescription(), coach.getImage(), isPossible);
    }
}
