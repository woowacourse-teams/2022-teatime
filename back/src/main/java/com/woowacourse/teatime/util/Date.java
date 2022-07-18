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

    public static LocalDateTime findToday(int year, int month) {
        LocalDate startDate = LocalDate.now();
        return LocalDateTime.of(startDate, LocalTime.MIN);
    }

    public static LocalDateTime findLastDay(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = YearMonth.from(startDate).atEndOfMonth();
        return LocalDateTime.of(endDate, LocalTime.MAX);
    }

    public static LocalDateTime findFirstTime(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    public static LocalDateTime findLastTime(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MAX);
    }
}
