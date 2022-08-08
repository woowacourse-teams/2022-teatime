package com.woowacourse.teatime.exception;

public class CannotSubmitBlankException extends BadRequestException {

    private static final String ERROR_MESSAGE = "빈칸이 포함된 시트를 제출할 수 없습니다.";

    public CannotSubmitBlankException() {
        super(ERROR_MESSAGE);
    }
}
