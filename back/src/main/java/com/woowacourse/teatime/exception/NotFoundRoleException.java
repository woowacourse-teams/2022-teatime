package com.woowacourse.teatime.exception;

public class NotFoundRoleException extends NotFoundException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 역할입니다.";

    public NotFoundRoleException() {
        super(DEFAULT_MESSAGE);
    }
}
