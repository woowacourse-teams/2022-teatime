package com.woowacourse.teatime.controller.dto;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CoachSaveRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String image;
}
