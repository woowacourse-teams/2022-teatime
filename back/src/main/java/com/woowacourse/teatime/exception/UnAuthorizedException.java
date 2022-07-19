package com.woowacourse.teatime.exception;

public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(String message) {
        super(message);
    }
}
