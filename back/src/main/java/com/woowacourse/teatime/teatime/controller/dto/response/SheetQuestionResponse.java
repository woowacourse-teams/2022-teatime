package com.woowacourse.teatime.teatime.controller.dto.response;

import com.woowacourse.teatime.teatime.domain.Question;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SheetQuestionResponse {

    private Integer questionNumber;

    private String questionContent;

    private Boolean isRequired;

    public static SheetQuestionResponse from(Question question) {
        return new SheetQuestionResponse(
                question.getNumber(),
                question.getContent(),
                question.getIsRequired());
    }
}
