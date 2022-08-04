package com.woowacourse.teatime.exception;

public class UnableToSubmitSheetException extends BadRequestException {

    private static final String ERROR_MESSAGE = "시트를 제출할 수 없습니다.";

    public UnableToSubmitSheetException() {
        super(ERROR_MESSAGE);
    }
}
