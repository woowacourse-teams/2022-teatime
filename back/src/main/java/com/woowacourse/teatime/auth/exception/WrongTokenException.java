package com.woowacourse.teatime.auth.exception;

import com.woowacourse.teatime.exception.BadRequestException;

public class WrongTokenException extends BadRequestException {

    private static final String ERROR_MESSAGE = "토큰이 잘못되었습니다.";

    public WrongTokenException() {
        super(ERROR_MESSAGE);
    }
}
