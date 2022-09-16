package com.woowacourse.teatime.teatime.exception;

import com.woowacourse.teatime.exception.BadRequestException;

public class UnableToDoneReservationException extends BadRequestException {

    private static final String ERROR_MESSAGE = "예약을 종료할 수 없습니다.";

    public UnableToDoneReservationException() {
        super(ERROR_MESSAGE);
    }
}
