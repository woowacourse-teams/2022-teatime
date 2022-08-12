package com.woowacourse.teatime.teatime.exception;

import com.woowacourse.teatime.exception.NotFoundException;

public class NotFoundCoachException extends NotFoundException {

    private static final String ERROR_MESSAGE = "존재하지 않는 코치입니다.";

    public NotFoundCoachException() {
        super(ERROR_MESSAGE);
    }
}
