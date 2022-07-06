package com.woowacourse.teatime.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @Column(nullable = false)
    private Boolean isPossible;

    public Schedule(LocalDateTime localDateTime) {
        validateDateTime(localDateTime);
        this.localDateTime = localDateTime;
        this.isPossible = true;
    }

    private void validateDateTime(LocalDateTime localDateTime) {
        if(localDateTime.getYear() < 2022) {
            throw new IllegalArgumentException("년도는 2022년 이후여야 합니다.");
        }
    }

    public boolean isSameTime(LocalDateTime localDateTime) {
        return this.localDateTime.equals(localDateTime);
    }

    public void reserve() {
        if (!this.isPossible) {
            throw new IllegalArgumentException("이미 예약이 되어 있습니다.");
        }
        this.isPossible = false;
    }

    public boolean isPossible() {
        return isPossible;
    }

}
