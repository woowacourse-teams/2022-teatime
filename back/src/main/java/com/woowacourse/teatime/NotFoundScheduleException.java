package com.woowacourse.teatime;

public class NotFoundScheduleException extends NotFoundException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 일정입니다.";

    public NotFoundScheduleException() {
        super(DEFAULT_MESSAGE);
    }
}
