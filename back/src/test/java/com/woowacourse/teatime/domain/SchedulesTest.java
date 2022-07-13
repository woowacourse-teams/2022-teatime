package com.woowacourse.teatime.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SchedulesTest {

    @Test
    @DisplayName("존재하지 않는 일정에 예약할 경우, 예외를 반환한다.")
    void reserve_exception_notExist() {
        Schedules schedules = new Schedules(new ArrayList<>());

        assertThatThrownBy(() -> schedules.reserve(LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 불가능한 일정에 예약할 경우, 예외를 반환한다.")
    void reserve_exception_impossible() {
        LocalDateTime now = LocalDateTime.now();
        Coach coach = new Coach("제이슨");

        Schedules schedules = new Schedules(List.of(new Schedule(coach, now)));
        schedules.reserve(now);

        assertThatThrownBy(() -> schedules.reserve(LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하는 예약 가능한 일정에 예약할 경우, 예약을 한다.")
    void reserve() {
        LocalDateTime now = LocalDateTime.now();
        Coach coach = new Coach("제이슨");

        Schedules schedules = new Schedules(List.of(new Schedule(coach, now)));
        assertDoesNotThrow(() -> schedules.reserve(now));
    }

    @Test
    @DisplayName("해당 날짜의 하루 예약을 모두 삭제한다.")
    void deleteByDate() {
        // given
        LocalDateTime july1_1 = LocalDateTime.of(2022, 7, 1, 1, 0, 0);
        LocalDateTime july1_2 = LocalDateTime.of(2022, 7, 1, 2, 0, 0);
        LocalDateTime july1_3 = LocalDateTime.of(2022, 7, 1, 3, 0, 0);
        LocalDateTime july2_1 = LocalDateTime.of(2022, 7, 2, 1, 0, 0);
        LocalDateTime july3_1 = LocalDateTime.of(2022, 7, 3, 1, 0, 0);
        Coach coach = new Coach("제이슨");

        Schedules schedules = new Schedules(List.of(new Schedule(coach, july1_1),
                new Schedule(coach, july1_2),
                new Schedule(coach, july1_3),
                new Schedule(coach, july2_1),
                new Schedule(coach, july3_1)));

        // when
        Schedules newSchedules = schedules.deleteByMonthAndDay(LocalDate.of(2022, 7, 1));

        // then
        assertThat(newSchedules.findByDay(1)).isEmpty();
    }

}
