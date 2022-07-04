package com.woowacourse.teatime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScheduleTest {

    @Test
    @DisplayName("예약을 하면 예약 불가능 상태로 변경된다.")
    void reserve_afterReserve() {
        LocalDateTime now = LocalDateTime.now();
        Schedule schedule = new Schedule(now);

        schedule.reserve();
        boolean result = schedule.isPossible();

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("예약을 하지 않았으면 예약 가능 상태이다.")
    void reserve_beforeReserve() {
        LocalDateTime now = LocalDateTime.now();
        Schedule schedule = new Schedule(now);

        boolean result = schedule.isPossible();

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("이미 예약된 일정에 예약을 하면 예외가 발생한다.")
    void reserve_exception() {
        LocalDateTime now = LocalDateTime.now();
        Schedule schedule = new Schedule(now);
        schedule.reserve();

        assertThatThrownBy(schedule::reserve)
                .isInstanceOf(IllegalArgumentException.class);
    }

}
