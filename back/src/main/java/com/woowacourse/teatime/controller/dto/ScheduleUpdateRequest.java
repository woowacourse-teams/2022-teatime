package com.woowacourse.teatime.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleUpdateRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private List<LocalDateTime> schedules;

    public ScheduleUpdateRequest(LocalDate date, List<LocalDateTime> schedules) {
        this.date = date;
        this.schedules = schedules;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<LocalDateTime> getSchedules() {
        return schedules;
    }
}
