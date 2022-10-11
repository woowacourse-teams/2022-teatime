package com.woowacourse.teatime.teatime.controller.dto.response;

import com.woowacourse.teatime.teatime.domain.CanceledSheet;
import com.woowacourse.teatime.teatime.domain.Sheet;
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
    private Boolean isRequired;

    private SheetDto(Sheet sheet) {
        this.questionNumber = sheet.getNumber();
        this.questionContent = sheet.getQuestionContent();
        this.answerContent = sheet.getAnswerContent();
        this.isRequired = sheet.getIsRequired();
    }

    private SheetDto(CanceledSheet sheet) {
        this.questionNumber = sheet.getNumber();
        this.questionContent = sheet.getQuestionContent();
        this.answerContent = sheet.getAnswerContent();
        this.isRequired = sheet.getIsRequired();
    }

    public static List<SheetDto> from(List<Sheet> sheets) {
        return sheets.stream()
                .map(SheetDto::new)
                .collect(Collectors.toList());
    }

    public static List<SheetDto> fromCanceled(List<CanceledSheet> sheets) {
        return sheets.stream()
                .map(SheetDto::new)
                .collect(Collectors.toList());
    }

    public static List<SheetDto> generateEmptySheet(List<Sheet> sheets) {
        return sheets.stream()
                .map(sheet -> new Sheet(sheet.getReservation(), sheet.getNumber(), sheet.getQuestionContent(), sheet.getIsRequired()))
                .map(SheetDto::new)
                .collect(Collectors.toList());
    }
}
