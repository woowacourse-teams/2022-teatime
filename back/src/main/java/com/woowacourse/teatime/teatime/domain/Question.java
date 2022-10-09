package com.woowacourse.teatime.teatime.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Coach coach;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isRequired;

    public Question(Coach coach, Integer number, String content, Boolean isRequired) {
        this.coach = coach;
        this.number = number;
        this.content = content;
        this.isRequired = isRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Question question = (Question) o;
        return Objects.equals(getCoach(), question.getCoach()) && Objects.equals(getNumber(), question.getNumber())
                && Objects.equals(getContent(), question.getContent()) && Objects.equals(getIsRequired(),
                question.getIsRequired());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCoach(), getNumber(), getContent());
    }
}
