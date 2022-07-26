package com.woowacourse.teatime.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Sheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Reservation reservation;

    @Column(nullable = false, unique = true)
    private Integer number;

    @Column(nullable = false)
    private String questionContent;

    @Lob
    private String answerContent;

    public Sheet(Reservation reservation, Integer number, String questionContent) {
        this.reservation = reservation;
        this.number = number;
        this.questionContent = questionContent;
        this.answerContent = null;
    }
}
