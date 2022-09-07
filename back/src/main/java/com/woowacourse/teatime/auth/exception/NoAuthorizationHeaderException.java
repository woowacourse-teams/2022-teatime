package com.woowacourse.teatime.auth.exception;

import com.woowacourse.teatime.exception.BadRequestException;

public class NoAuthorizationHeaderException extends BadRequestException {

    private static final String ERROR_MESSAGE = "헤더에 토큰이 존재하지 않습니다.";

    public NoAuthorizationHeaderException() {
        super(ERROR_MESSAGE);
    }
}
