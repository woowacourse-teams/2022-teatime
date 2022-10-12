package com.woowacourse.teatime.teatime.controller.dto.response;

import com.woowacourse.teatime.teatime.domain.Question;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SheetQuestionsResponse {

    private List<SheetQuestionResponse> questions;

    public static SheetQuestionsResponse from(List<Question> savedQuestions) {
        return new SheetQuestionsResponse(savedQuestions.stream()
                .map(SheetQuestionResponse::from)
                .collect(Collectors.toList()));
    }
}
