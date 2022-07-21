package com.woowacourse.teatime.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Schedules {

    private final List<Schedule> schedules;

    public Schedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Integer> findDays() {
        return schedules.stream()
                .map(Schedule::getLocalDateTime)
                .map(LocalDateTime::getDayOfMonth)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Schedule> findByDay(int day) {
        return schedules.stream()
                .filter(schedule -> schedule.isSameDay(day))
                .collect(Collectors.toList());
    }
}
