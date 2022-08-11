package com.woowacourse.teatime.exception;

import com.woowacourse.exception.BadRequestException;

public class UnCancellableReservationException extends BadRequestException {

    private static final String ERROR_MESSAGE = "예약을 취소할 수 없습니다.";

    public UnCancellableReservationException() {
        super(ERROR_MESSAGE);
    }
}
