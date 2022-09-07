package com.woowacourse.teatime.teatime.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.teatime.teatime.exception.AlreadyReservedException;
import com.woowacourse.teatime.teatime.exception.InvalidYearException;
import com.woowacourse.teatime.teatime.fixture.DomainFixture;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScheduleTest {

    @DisplayName("예약을 하면 예약 불가능 상태로 변경된다.")
    @Test
    void reserve_afterReserve() {
        Schedule schedule = new Schedule(DomainFixture.COACH_BROWN, DomainFixture.DATE_TIME);

        schedule.reserve();
        boolean result = schedule.isPossible();

        assertThat(result).isFalse();
    }

    @DisplayName("예약을 하지 않았으면 예약 가능 상태이다.")
    @Test
    void reserve_beforeReserve() {
        Schedule schedule = new Schedule(DomainFixture.COACH_BROWN, DomainFixture.DATE_TIME);

        boolean result = schedule.isPossible();

        assertThat(result).isTrue();
    }

    @DisplayName("이미 예약된 일정에 예약을 하면 예외가 발생한다.")
    @Test
    void reserve_exception() {
        Schedule schedule = new Schedule(DomainFixture.COACH_BROWN, DomainFixture.DATE_TIME);
        schedule.reserve();

        assertThatThrownBy(schedule::reserve)
                .isInstanceOf(AlreadyReservedException.class);
    }

    @DisplayName("스케줄이 2022년 이전이면 예외가 발생한다.")
    @Test
    void schedule_boundException() {
        LocalDateTime dateTime = LocalDateTime.of(2021, 1, 1, 0, 0);

        assertThatThrownBy(() -> new Schedule(DomainFixture.COACH_BROWN, dateTime))
                .isInstanceOf(InvalidYearException.class);
    }
}
