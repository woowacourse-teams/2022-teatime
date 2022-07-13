package com.woowacourse.teatime;

public class NotExistedCoachException extends NotFoundException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 코치입니다.";

    public NotExistedCoachException() {
        super(DEFAULT_MESSAGE);
    }
}
