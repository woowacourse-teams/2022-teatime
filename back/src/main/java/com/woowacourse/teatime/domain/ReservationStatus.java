package com.woowacourse.teatime.domain;

public enum ReservationStatus {
    BEFORE_APPROVED(() -> Comparator.comparing(Reservation::getId)),
    APPROVED(() -> Comparator.comparing(Reservation::getScheduleDateTime)),
    IN_PROGRESS(() -> Comparator.comparing(Reservation::getScheduleDateTime)),
    DONE(() -> Comparator.comparing(Reservation::getScheduleDateTime));

    private final Supplier<Comparator<Reservation>> comparator;

    ReservationStatus(Supplier<Comparator<Reservation>> comparator) {
        this.comparator = comparator;
    }

    public boolean isSameStatus(ReservationStatus status) {
        return this.equals(status);
    }

    public static List<Reservation> classifyReservations(ReservationStatus status, List<Reservation> reservations) {
        return reservations.stream()
                .filter(reservation -> reservation.isSameStatus(status))
                .sorted(status.comparator.get())
                .collect(Collectors.toList());
    }
}
