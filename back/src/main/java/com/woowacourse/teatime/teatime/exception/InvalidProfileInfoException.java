package com.woowacourse.teatime.teatime.exception;

import com.woowacourse.teatime.exception.BadRequestException;

public class InvalidProfileInfoException extends BadRequestException {

    private static final String ERROR_MESSAGE = "팔요한 정보를 모두 입력해 주세요.";

    public InvalidProfileInfoException() {
        super(ERROR_MESSAGE);
    }
}
