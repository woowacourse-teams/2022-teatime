package com.woowacourse.teatime.teatime.repository.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoachWithPossible {

    private Long id;
    private String name;
    private String description;
    private String image;
    private Boolean isPossible;
    private Boolean isPokable;
}
