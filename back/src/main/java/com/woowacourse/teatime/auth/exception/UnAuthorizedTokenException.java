package com.woowacourse.teatime.auth.exception;

import com.woowacourse.teatime.exception.UnAuthorizedException;

public class UnAuthorizedTokenException extends UnAuthorizedException {

    private static final String ERROR_MESSAGE = "유효하지 않은 토큰입니다.";

    public UnAuthorizedTokenException() {
        super(ERROR_MESSAGE);
    }
}
