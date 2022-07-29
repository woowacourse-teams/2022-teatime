package com.woowacourse.teatime.controller.dto.response;

import com.woowacourse.teatime.domain.Sheet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SheetDto {

    private Integer questionNumber;
    private String questionContent;
    private String answerContent;

    public static List<SheetDto> of(List<Sheet> sheets) {
        return sheets.stream()
                .map(SheetDto::new)
                .collect(Collectors.toList());
    }

    private SheetDto(Sheet sheet) {
        this.questionNumber = sheet.getNumber();
        this.questionContent = sheet.getQuestionContent();
        this.answerContent = sheet.getAnswerContent();
    }
}
