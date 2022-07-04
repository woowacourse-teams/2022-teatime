package com.woowacourse.teatime;

import java.time.LocalDateTime;
import java.util.List;

public class Schedules {

    private final List<Schedule> schedules;

    public Schedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public boolean isExist(LocalDateTime localDateTime) {
        return schedules.stream()
                .anyMatch(schedule -> schedule.isSameTime(localDateTime));
    }
}
