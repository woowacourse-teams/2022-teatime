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
public class SheetAnswerUpdateDto {

    @NotNull
    private Integer questionNumber;

    @NotBlank
    private String questionContent;

    @NotBlank
    private String answerContent;

    @NotNull
    private Boolean isRequired;
}
