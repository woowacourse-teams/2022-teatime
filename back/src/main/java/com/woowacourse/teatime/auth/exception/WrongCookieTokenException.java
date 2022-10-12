package com.woowacourse.teatime.auth.exception;

import com.woowacourse.teatime.exception.BadRequestException;

public class WrongCookieTokenException extends BadRequestException {

    private static final String ERROR_MESSAGE = "쿠키에 담긴 토큰이 잘못되었습니다.";

    public WrongCookieTokenException() {
        super(ERROR_MESSAGE);
    }
}
