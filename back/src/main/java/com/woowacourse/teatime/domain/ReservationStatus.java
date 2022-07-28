package com.woowacourse.teatime.domain;

public enum ReservationStatus {
    BEFORE_APPROVED,
    APPROVED,
    IN_PROGRESS;

    public static boolean isBeforeApproved(ReservationStatus status) {
        return status.equals(BEFORE_APPROVED);
    }
}
