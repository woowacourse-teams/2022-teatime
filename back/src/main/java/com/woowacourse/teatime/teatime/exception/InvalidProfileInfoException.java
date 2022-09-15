package com.woowacourse.teatime.teatime.exception;

import com.woowacourse.teatime.exception.BadRequestException;

public class InvalidProfileInfoException extends BadRequestException {

    private static final String ERROR_MESSAGE = "유효하지 않은 프로필 정보입니다.";

    public InvalidProfileInfoException() {
        super(ERROR_MESSAGE);
    }
}
