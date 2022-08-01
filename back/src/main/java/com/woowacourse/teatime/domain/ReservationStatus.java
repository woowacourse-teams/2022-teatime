package com.woowacourse.teatime.domain;

import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum ReservationStatus {
    BEFORE_APPROVED(() -> Comparator.comparing(Reservation::getId)),
    APPROVED(() -> Comparator.comparing(Reservation::getLocalDateTime)),
    IN_PROGRESS(() -> Comparator.comparing(Reservation::getLocalDateTime));

    private final Supplier<Comparator<Reservation>> comparator;

    ReservationStatus(Supplier<Comparator<Reservation>> comparator) {
        this.comparator = comparator;
    }

    public static boolean isBeforeApproved(ReservationStatus status) {
        return status.equals(BEFORE_APPROVED);
    }

    public static boolean isApproved(ReservationStatus status) {
        return status.equals(APPROVED);
    }

    public static List<Reservation> classifyReservations(ReservationStatus status, List<Reservation> reservations) {
        return reservations.stream()
                .filter(reservation -> reservation.isSameStatus(status))
                .sorted(status.comparator.get())
                .collect(Collectors.toList());
    }
}
