package com.woowacourse.teatime.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.teatime.AlreadyReservedException;
import com.woowacourse.teatime.NotFoundScheduleException;
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
                .isInstanceOf(NotFoundScheduleException.class);
    }

    @Test
    @DisplayName("예약 불가능한 일정에 예약할 경우, 예외를 반환한다.")
    void reserve_exception_impossible() {
        LocalDateTime now = LocalDateTime.now();
        Coach coach = new Coach("제이슨");

        Schedules schedules = new Schedules(List.of(new Schedule(coach, now)));
        schedules.reserve(now);

        assertThatThrownBy(() -> schedules.reserve(now))
                .isInstanceOf(AlreadyReservedException.class);
    }

    @Test
    @DisplayName("존재하는 예약 가능한 일정에 예약할 경우, 예약을 한다.")
    void reserve() {
        LocalDateTime now = LocalDateTime.now();
        Coach coach = new Coach("제이슨");

        Schedules schedules = new Schedules(List.of(new Schedule(coach, now)));
        assertDoesNotThrow(() -> schedules.reserve(now));
    }
}
