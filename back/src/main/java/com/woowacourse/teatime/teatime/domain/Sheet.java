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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Sheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Reservation reservation;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private String questionContent;

    @Column(nullable = false)
    private Boolean isRequired;

    @Lob
    private String answerContent;

    public Sheet(Reservation reservation, Integer number, String questionContent) {
        this.reservation = reservation;
        this.number = number;
        this.questionContent = questionContent;
        this.isRequired = true;
        this.answerContent = null;
    }

    public Sheet(Reservation reservation, Integer number, String questionContent, Boolean isRequired) {
        this.reservation = reservation;
        this.number = number;
        this.questionContent = questionContent;
        this.isRequired = isRequired;
        this.answerContent = null;
    }

    public void modifyAnswer(int number, String content) {
        if (this.number == number) {
            this.answerContent = content;
        }
    }
}
