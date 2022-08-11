package com.woowacourse.teatime.exception;

import com.woowacourse.exception.NotFoundException;

public class NotFoundRoleException extends NotFoundException {

    private static final String ERROR_MESSAGE = "존재하지 않는 역할입니다.";

    public NotFoundRoleException() {
        super(ERROR_MESSAGE);
    }
}
