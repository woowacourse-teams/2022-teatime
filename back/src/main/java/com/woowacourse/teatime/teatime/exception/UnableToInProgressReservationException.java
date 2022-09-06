package com.woowacourse.teatime.teatime.exception;

import com.woowacourse.teatime.exception.BadRequestException;

public class UnableToInProgressReservationException extends BadRequestException {

    public UnableToInProgressReservationException(String message) {
        super(message);
    }
}
