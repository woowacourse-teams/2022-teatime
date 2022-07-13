package com.woowacourse.teatime;

import java.time.LocalDateTime;

public class Schedule {

    private final LocalDateTime localDateTime;
    private boolean isPossible;

    public Schedule(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        this.isPossible = true;
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
