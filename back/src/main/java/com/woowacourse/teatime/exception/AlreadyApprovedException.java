package com.woowacourse.teatime.exception;

import com.woowacourse.exception.BadRequestException;

public class AlreadyApprovedException extends BadRequestException {

    private static final String ERROR_MESSAGE = "이미 승인이 되어 있습니다.";

    public AlreadyApprovedException() {
        super(ERROR_MESSAGE);
    }
}
