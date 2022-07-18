package com.woowacourse.teatime.domain;

import static com.woowacourse.teatime.fixture.DomainFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @DisplayName("면담을 승인한다.")
    @Test
    void approveReservation() {
        Schedule schedule = new Schedule(COACH_BROWN, DATE_TIME);
        Reservation reservation = new Reservation(schedule, CREW);

        reservation.approve();

        assertThat(reservation.isApproved()).isTrue();
    }
}
