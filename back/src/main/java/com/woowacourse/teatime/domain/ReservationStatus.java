package com.woowacourse.teatime.domain;

public enum ReservationStatus {
    BEFORE_APPROVED,
    APPROVED,
    IN_PROGRESS;

    public boolean isBeforeApproved() {
        return this.equals(BEFORE_APPROVED);
    }

    public boolean isApproved() {
        return this.equals(APPROVED);
    }
}
