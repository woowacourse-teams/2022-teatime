package com.woowacourse.teatime.teatime.exception;

import com.woowacourse.teatime.exception.NotFoundException;

public class NotFoundCrewException extends NotFoundException {

    private static final String ERROR_MESSAGE = "존재하지 않는 크루입니다.";

    public NotFoundCrewException() {
        super(ERROR_MESSAGE);
    }
}
