package com.woowacourse.teatime.teatime.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class CanceledSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private CanceledReservation canceledReservation;

    @Column(nullable = false, unique = true)
    private Integer number;

    @Column(nullable = false)
    private String questionContent;

    @Lob
    private String answerContent;

    @Column(nullable = false)
    private Boolean isRequired;

    private CanceledSheet(CanceledReservation canceledReservation, Integer number, String questionContent,
                          String answerContent, Boolean isRequired) {
        this.canceledReservation = canceledReservation;
        this.number = number;
        this.questionContent = questionContent;
        this.answerContent = answerContent;
        this.isRequired = isRequired;
    }

    public static CanceledSheet from(CanceledReservation canceledReservation, Sheet sheet) {
        return new CanceledSheet(
                canceledReservation,
                sheet.getNumber(),
                sheet.getQuestionContent(),
                sheet.getAnswerContent(),
                sheet.getIsRequired());
    }
}
