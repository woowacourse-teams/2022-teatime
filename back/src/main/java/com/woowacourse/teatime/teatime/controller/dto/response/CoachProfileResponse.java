package com.woowacourse.teatime.teatime.controller.dto.response;


import com.woowacourse.teatime.teatime.domain.Coach;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoachProfileResponse {

    private String name;
    private String description;
    private String image;
    private Boolean isPokable;

    public CoachProfileResponse(Coach coach) {
        this(coach.getName(), coach.getDescription(), coach.getImage(), coach.getIsPokable());
    }
}
