package com.woowacourse.teatime.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;

public class Date {
    public static LocalDateTime findFirstDay(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        return LocalDateTime.of(startDate, LocalTime.MIN);
    }

    public static LocalDateTime findEndDay(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = YearMonth.from(startDate).atEndOfMonth();
        return LocalDateTime.of(endDate, LocalTime.MAX);
    }
}
