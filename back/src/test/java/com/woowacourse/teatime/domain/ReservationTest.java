package com.woowacourse.teatime.domain;

import static com.woowacourse.teatime.fixture.DomainFixture.COACH_BROWN;
import static com.woowacourse.teatime.fixture.DomainFixture.CREW;
import static com.woowacourse.teatime.fixture.DomainFixture.DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.teatime.exception.AlreadyApprovedException;
import com.woowacourse.teatime.exception.InvalidCancelException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    private Reservation reservation;
    private Schedule schedule;

    @BeforeEach
    void setUp() {
        schedule = new Schedule(COACH_BROWN, DATE_TIME);
        this.reservation = new Reservation(schedule, CREW);
    }

    @DisplayName("면담을 승인한다.")
    @Test
    void confirm_approve() {
        boolean 승인을_한다 = true;

        reservation.confirm(승인을_한다);

        assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.APPROVED);
    }

    @DisplayName("승인 전, 면담을 취소한다.")
    @Test
    void conform_denyApproval() {
        boolean 승인을_거절한다 = false;

        reservation.confirm(승인을_거절한다);

        assertThat(schedule.getIsPossible()).isTrue();
    }

    @DisplayName("승인이 되어 있는 상태에서 승인 요청을 하면 에러가 발생한다.")
    @Test
    void confirm_invalid() {
        reservation.confirm(true);
        boolean 승인을_한다 = true;

        assertThatThrownBy(() -> reservation.confirm(승인을_한다))
                .isInstanceOf(AlreadyApprovedException.class);
    }

    @DisplayName("예약을 취소할 수 있다.")
    @Test
    void cancel_coach() {
        reservation.confirm(true);
        reservation.cancel();

        assertThat(schedule.getIsPossible()).isTrue();
    }

    @DisplayName("예약을 취소할 때, 상태가 승인상태가 아닌 경우 에러가 발생한다.")
    @Test
    void cancel_InvalidCancelException() {
        assertThatThrownBy(() -> reservation.cancel())
                .isInstanceOf(InvalidCancelException.class);

    }
}
