package com.woowacourse.teatime.exception;

public class NotExistedCrewException extends UnAuthorizedException {

    private static final String ERROR_MESSAGE = "존재하지 않는 크루입니다.";

    public NotExistedCrewException() {
        super(ERROR_MESSAGE);
    }
}
