package com.woowacourse.teatime;

public class NotExistedScheduleException extends NotFoundException{

    private static final String DEFAULT_MESSAGE = "존재하지 않는 스케줄입니다.";

    public NotExistedScheduleException() {
        super(DEFAULT_MESSAGE);
    }
}
