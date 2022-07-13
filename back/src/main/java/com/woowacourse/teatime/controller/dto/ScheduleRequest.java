package com.woowacourse.teatime.controller.dto;

public class ScheduleRequest {

    private final Integer year;
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
