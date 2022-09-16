package com.woowacourse.teatime.teatime.exception;

import com.woowacourse.teatime.exception.BadRequestException;

public class UnableToCancelReservationException extends BadRequestException {

    private static final String ERROR_MESSAGE = "예약을 취소할 수 없습니다.";

    public UnableToCancelReservationException() {
        super(ERROR_MESSAGE);
    }
}
