package com.woowacourse.teatime.exception;

public class InvalidCancelException extends BadRequestException {

    private static final String ERROR_MESSAGE = "예약을 취소할 수 없습니다.";

    public InvalidCancelException() {
        super(ERROR_MESSAGE);
    }
}
