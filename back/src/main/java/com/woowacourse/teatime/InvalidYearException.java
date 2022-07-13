package com.woowacourse.teatime;

public class InvalidYearException extends BadRequestException {
    private static final String DEFAULT_MESSAGE = "년도는 2022년 이후여야 합니다.";

    public InvalidYearException() {
        super(DEFAULT_MESSAGE);
    }
}
