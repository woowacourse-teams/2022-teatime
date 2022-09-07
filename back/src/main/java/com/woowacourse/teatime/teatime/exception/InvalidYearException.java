package com.woowacourse.teatime.teatime.exception;

import com.woowacourse.teatime.exception.BadRequestException;

public class InvalidYearException extends BadRequestException {

    private static final String ERROR_MESSAGE = "년도는 2022년 이후여야 합니다.";

    public InvalidYearException() {
        super(ERROR_MESSAGE);
    }
}
