package com.woowacourse.teatime.domain;

import java.util.List;

public class Reservations {

    private final List<Reservation> reservations;

    public Reservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void updateStatus() {
        reservations.stream()
                .filter(reservation -> reservation.isSameStatus(ReservationStatus.APPROVED))
                .forEach(Reservation::update);
    }
}
