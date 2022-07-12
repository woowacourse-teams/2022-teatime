package com.woowacourse.teatime.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Schedules {

    private final List<Schedule> schedules;

    public Schedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public void reserve(LocalDateTime localDateTime) {
        Schedule schedule = findSchedule(localDateTime);
        schedule.reserve();
    }

    private Schedule findSchedule(LocalDateTime localDateTime) {
        return schedules.stream()
                .filter(schedule -> schedule.isSameTime(localDateTime))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일정입니다."));
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
