package com.woowacourse.teatime.teatime.controller.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SheetQuestionUpdateRequest {

    @NotNull
    private Integer questionNumber;

    @NotBlank
    private String questionContent;

    @NotNull
    private Boolean isRequired;
}
