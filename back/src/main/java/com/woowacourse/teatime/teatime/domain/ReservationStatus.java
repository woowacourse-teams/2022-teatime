package com.woowacourse.teatime.teatime.domain;

import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum ReservationStatus {

    BEFORE_APPROVED(() -> Comparator.comparing(Reservation::getId)),
    APPROVED(() -> Comparator.comparing(Reservation::getScheduleDateTime)),
    IN_PROGRESS(() -> Comparator.comparing(Reservation::getScheduleDateTime)),
    DONE(() -> Comparator.comparing(Reservation::getScheduleDateTime)),
    CANCELED(() -> Comparator.comparing(Reservation::getScheduleDateTime));

    private final Supplier<Comparator<Reservation>> comparator;

    ReservationStatus(Supplier<Comparator<Reservation>> comparator) {
        this.comparator = comparator;
    }

    public static List<Reservation> classifyReservations(ReservationStatus status, List<Reservation> reservations) {
        return reservations.stream()
                .filter(reservation -> reservation.isReservationStatus(status))
                .sorted(status.comparator.get())
                .collect(Collectors.toList());
    }
}
