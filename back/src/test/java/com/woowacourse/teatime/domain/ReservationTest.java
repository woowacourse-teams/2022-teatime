package com.woowacourse.teatime.domain;

import static com.woowacourse.teatime.fixture.DomainFixture.COACH_BROWN;
import static com.woowacourse.teatime.fixture.DomainFixture.CREW;
import static com.woowacourse.teatime.fixture.DomainFixture.DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.teatime.exception.AlreadyApprovedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        Schedule schedule = new Schedule(COACH_BROWN, DATE_TIME);
        this.reservation = new Reservation(schedule, CREW);
    }

    @DisplayName("면담을 승인한다.")
    @Test
    void approve() {
        boolean 승인을_한다 = true;

        assertThat(reservation.isApproved(승인을_한다)).isTrue();
    }

    @DisplayName("승인 전, 면담을 취소한다.")
    @Test
    void deny_approval() {
        boolean 승인을_거절한다 = false;

        assertThat(reservation.isApproved(승인을_거절한다)).isFalse();
    }

    @DisplayName("승인이 되어 있는 상태에서 승인 요청을 하면 에러가 발생한다.")
    @Test
    void name() {
        reservation.isApproved(true);
        boolean 승인을_한다 = true;

        assertThatThrownBy(() -> reservation.isApproved(승인을_한다))
                .isInstanceOf(AlreadyApprovedException.class);
    }
}
