package com.woowacourse.teatime.exception;

public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException() {
        this("권한이 없습니다.");
    }
}
