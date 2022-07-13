package com.woowacourse.teatime.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Coach coach;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @Column(nullable = false)
    private Boolean isPossible;

    public Schedule(Coach coach, LocalDateTime localDateTime) {
        this.coach = coach;
        validateDateTime(localDateTime);
        this.localDateTime = localDateTime;
        this.isPossible = true;
    }

    private void validateDateTime(LocalDateTime localDateTime) {
        if (localDateTime.getYear() < 2022) {
            throw new IllegalArgumentException("년도는 2022년 이후여야 합니다.");
        }
    }

    public boolean isSame(LocalDateTime localDateTime) {
        return this.localDateTime.equals(localDateTime);
    }

    public boolean isSameDate(LocalDate other) {
        LocalDate localDate = this.localDateTime.toLocalDate();
        return localDate.equals(other);
    }

    public void reserve() {
        if (!this.isPossible) {
            throw new IllegalArgumentException("이미 예약이 되어 있습니다.");
        }
        this.isPossible = false;
    }

    public boolean isSameDay(int day) {
        return this.localDateTime.getDayOfMonth() == day;
    }

    public boolean isPossible() {
        return isPossible;
    }
}
