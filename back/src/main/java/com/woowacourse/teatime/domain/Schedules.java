package com.woowacourse.teatime.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Schedules {

    public final List<Schedule> schedules;

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
}
