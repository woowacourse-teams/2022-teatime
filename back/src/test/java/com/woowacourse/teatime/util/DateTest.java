package com.woowacourse.teatime.util;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class
DateTest {

    @DisplayName("지난 년도를 입력하면 예외를 반환한다.")
    @Test
    void findFirstDay_invalidYear() {
        LocalDate past = LocalDate.now().minusYears(1L);
        assertThatThrownBy(() -> Date.findFirstDateTime(past.getYear(), past.getMonthValue()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("지난 월을 입력하면 예외를 반환한다.")
    @Test
    void findFirstDay_invalidMonth() {
        LocalDate past = LocalDate.now().minusMonths(1L);
        assertThatThrownBy(() -> Date.findFirstDateTime(past.getYear(), past.getMonthValue()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이번 달을 입력하면 오늘 날짜를 반환한다.")
    @Test
    void findFirstDay_thisMonth() {
        LocalDate now = LocalDate.now();
        LocalDateTime firstDay = Date.findFirstDateTime(now.getYear(), now.getMonthValue());
        assertThat(firstDay.toLocalDate()).isEqualTo(DATE_TIME.toLocalDate());
    }

    @DisplayName("다음 달을 입력하면 다음 달의 첫날을 반환한다.")
    @Test
    void findFirstDay_nextMonth() {
        LocalDate future = LocalDate.now().plusMonths(1L);
        LocalDateTime firstDay = Date.findFirstDateTime(future.getYear(), future.getMonthValue());
        LocalDate expectedDate = LocalDate.of(future.getYear(), future.getMonthValue(), 1);
        assertThat(firstDay).isEqualTo(LocalDateTime.of(expectedDate, LocalTime.MIN));
    }

    @DisplayName("패턴을 입력하면 localDateTiem을 패턴에 맞게 string으로 반환한다.")
    @Test
    void formatDate() {
        //given
        LocalDateTime dateTime = LocalDateTime.of(2022,9,15,13,30, 33);

        //when
        String actual = Date.formatDate("yyyy-MM-dd HH:mm", dateTime);

        //then
        String expected = "2022-09-15 13:30";
        assertThat(actual).isEqualTo(expected);
    }
}
