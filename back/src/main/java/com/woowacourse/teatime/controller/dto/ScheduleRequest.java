package com.woowacourse.teatime.controller.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ScheduleRequest {

    @NotNull
    @Min(2022)
    private final Integer year;

    @NotNull
    @Min(1)
    @Max(12)
    private final Integer month;

    public ScheduleRequest(Integer year, Integer month) {
        this.year = year;
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }
}
