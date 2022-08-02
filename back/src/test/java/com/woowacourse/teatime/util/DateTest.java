package com.woowacourse.teatime.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateTest {

    @DisplayName("지난 년도를 입력하면 예외를 반환한다.")
    @Test
    void findFirstDay_invalidYear() {
        LocalDate past = LocalDate.now().minusYears(1L);
        assertThatThrownBy(() -> Date.findFirstDay(past.getYear(), past.getMonthValue()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("지난 월을 입력하면 예외를 반환한다.")
    @Test
    void findFirstDay_invalidMonth() {
        LocalDate past = LocalDate.now().minusMonths(1L);
        assertThatThrownBy(() -> Date.findFirstDay(past.getYear(), past.getMonthValue()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이번 달을 입력하면 오늘 날짜를 반환한다.")
    @Test
    void findFirstDay_thisMonth() {
        LocalDate now = LocalDate.now();
        LocalDateTime firstDay = Date.findFirstDay(now.getYear(), now.getMonthValue());
        assertThat(firstDay).isEqualTo(LocalDateTime.of(now, LocalTime.MIN));
    }

    @DisplayName("다음 달을 입력하면 다음 달의 첫날을 반환한다.")
    @Test
    void findFirstDay_nextMonth() {
        LocalDate future = LocalDate.now().plusMonths(1L);
        LocalDateTime firstDay = Date.findFirstDay(future.getYear(), future.getMonthValue());
        LocalDate expectedDate = LocalDate.of(future.getYear(), future.getMonthValue(), 1);
        assertThat(firstDay).isEqualTo(LocalDateTime.of(expectedDate, LocalTime.MIN));
    }
}
