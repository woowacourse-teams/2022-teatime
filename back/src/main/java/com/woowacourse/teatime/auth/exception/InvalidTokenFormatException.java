package com.woowacourse.teatime.auth.exception;

import com.woowacourse.teatime.exception.BadRequestException;

public class InvalidTokenFormatException extends BadRequestException {

    private static final String ERROR_MESSAGE = "토큰 형식이 잘못 되었습니다.";

    public InvalidTokenFormatException() {
        super(ERROR_MESSAGE);
    }
}
