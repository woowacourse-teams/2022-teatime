package com.woowacourse.teatime.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;

public class Date {
    public static LocalDateTime findFirstDay(int year, int month) {
        LocalDate today = LocalDate.now();
        validateYearAndMonth(year, month, today);
        LocalDate startDate = LocalDate.of(year, month, 1);
        if (year == today.getYear() && month == today.getMonthValue()) {
            startDate = LocalDate.now();
        }
        return LocalDateTime.of(startDate, LocalTime.MIN);
    }

    private static void validateYearAndMonth(int year, int month, LocalDate today) {
        if (year < today.getYear()) {
            throw new IllegalArgumentException("지난 년도에 대한 일정은 조회할 수 없습니다.");
        }
        if (year == today.getYear() && month < today.getMonthValue()) {
            throw new IllegalArgumentException("지난 월에 대한 일정은 조회할 수 없습니다.");
        }
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
