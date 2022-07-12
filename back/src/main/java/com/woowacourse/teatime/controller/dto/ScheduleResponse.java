package com.woowacourse.teatime.controller.dto;

import com.woowacourse.teatime.domain.Schedule;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleResponse {

    private Integer day;
    private List<ScheduleDto> schedules;

    private ScheduleResponse() {
    }

    private ScheduleResponse(Integer day, List<ScheduleDto> schedules) {
        this.day = day;
        this.schedules = schedules;
    }

    public static ScheduleResponse of(int day, List<Schedule> schedules) {
        List<ScheduleDto> scheduleDtos = schedules.stream()
                .map(ScheduleDto::new)
                .collect(Collectors.toList());
        return new ScheduleResponse(day, scheduleDtos);
    }

    public Integer getDay() {
        return day;
    }

    public List<ScheduleDto> getSchedules() {
        return schedules;
    }
}
