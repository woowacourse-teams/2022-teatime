package com.woowacourse.teatime;

import java.time.LocalDateTime;
import java.util.Objects;

public class Schedule {

    private final LocalDateTime localDateTime;

    public Schedule(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
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
        return Objects.equals(localDateTime, schedule.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localDateTime);
    }

    public boolean isSameTime(LocalDateTime localDateTime) {
        return this.localDateTime.equals(localDateTime);
    }
}
