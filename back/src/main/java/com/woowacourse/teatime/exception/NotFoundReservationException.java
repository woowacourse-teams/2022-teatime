package com.woowacourse.teatime.exception;

import com.woowacourse.exception.NotFoundException;

public class NotFoundReservationException extends NotFoundException {

    private static final String ERROR_MESSAGE = "존재하지 않는 예약입니다.";

    public NotFoundReservationException() {
        super(ERROR_MESSAGE);
    }
}
