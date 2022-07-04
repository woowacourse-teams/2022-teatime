package com.woowacourse.teatime;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SchedulesTest {

    @Test
    @DisplayName("존재하지 않는 일정일 경우 false를 반환한다.")
    void isExist_false() {
        Schedules schedules = new Schedules(new ArrayList<>());
        boolean result = schedules.isExist(LocalDateTime.now());

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("존재하는 일정일 경우 true를 반환한다.")
    void isExist_true() {
        LocalDateTime now = LocalDateTime.now();

        Schedules schedules = new Schedules(List.of(new Schedule(now)));
        boolean result = schedules.isExist(now);

        assertThat(result).isTrue();
    }

}
