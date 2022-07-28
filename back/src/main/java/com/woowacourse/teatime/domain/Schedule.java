package com.woowacourse.teatime.domain;

import com.woowacourse.teatime.exception.AlreadyReservedException;
import com.woowacourse.teatime.exception.InvalidYearException;
import java.time.LocalDateTime;
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
            throw new InvalidYearException();
        }
    }

    public boolean isSame(LocalDateTime localDateTime) {
        return this.localDateTime.equals(localDateTime);
    }

    public void reserve() {
        if (!this.isPossible) {
            throw new AlreadyReservedException();
        }
        this.isPossible = false;
    }

    public boolean isSameDay(int day) {
        return this.localDateTime.getDayOfMonth() == day;
    }

    public boolean isPossible() {
        return isPossible;
    }

    public boolean isSameCoach(Long coachId) {
        return this.coach.getId().equals(coachId);
    }

    public void init() {
        this.isPossible = true;
    }
}

