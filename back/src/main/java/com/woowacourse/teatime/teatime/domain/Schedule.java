package com.woowacourse.teatime.teatime.domain;

import com.woowacourse.teatime.teatime.exception.AlreadyReservedException;
import com.woowacourse.teatime.teatime.exception.InvalidYearException;
import java.time.LocalDateTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Schedule)) {
            return false;
        }
        Schedule schedule = (Schedule) o;
        return Objects.equals(getCoach(), schedule.getCoach()) && Objects.equals(getLocalDateTime(),
                schedule.getLocalDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCoach(), getLocalDateTime());
    }
}

