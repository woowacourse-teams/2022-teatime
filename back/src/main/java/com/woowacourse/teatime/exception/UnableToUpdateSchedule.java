package com.woowacourse.teatime.exception;

public class UnableToUpdateSchedule extends BadRequestException {

    private static final String ERROR_MESSAGE = "일정을 수정할 수 없습니다.";

    public UnableToUpdateSchedule() {
        super(ERROR_MESSAGE);
    }
}
