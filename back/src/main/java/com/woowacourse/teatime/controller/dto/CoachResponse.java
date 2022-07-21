package com.woowacourse.teatime.controller.dto;

import com.woowacourse.teatime.domain.Coach;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoachResponse {

    private Long id;
    private String name;
    private String description;
    private String image;

    public CoachResponse(Coach coach) {
        this(coach.getId(), coach.getName(), coach.getDescription(), coach.getImage());
    }
}
