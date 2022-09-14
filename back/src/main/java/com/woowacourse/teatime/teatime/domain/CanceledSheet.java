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
    private CanceledReservation reservation;

    @Column(nullable = false, unique = true)
    private Integer number;

    @Column(nullable = false)
    private String questionContent;

    @Lob
    private String answerContent;

    public CanceledSheet(CanceledReservation reservation, Integer number, String questionContent) {
        this.reservation = reservation;
        this.number = number;
        this.questionContent = questionContent;
        this.answerContent = null;
    }

    public void modifyAnswer(int number, String content) {
        if (this.number == number) {
            this.answerContent = content;
        }
    }
}
