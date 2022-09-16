package com.woowacourse.teatime.teatime.exception;

import com.woowacourse.teatime.exception.BadRequestException;

public class UnableToUpdateScheduleException extends BadRequestException {

    private static final String ERROR_MESSAGE = "일정을 수정할 수 없습니다.";

    public UnableToUpdateScheduleException() {
        super(ERROR_MESSAGE);
    }
}
