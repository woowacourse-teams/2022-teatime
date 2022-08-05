package com.woowacourse.teatime.exception;

public class UnModifiableSheetException extends BadRequestException {

    private static final String ERROR_MESSAGE = "면담 시트를 수정할 수 없습니다.";

    public UnModifiableSheetException() {
        super(ERROR_MESSAGE);
    }
}
