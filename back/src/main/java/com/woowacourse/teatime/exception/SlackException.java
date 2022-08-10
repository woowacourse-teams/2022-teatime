package com.woowacourse.teatime.exception;

public class SlackException extends RuntimeException{

    private static final String ERROR_MESSAGE = "슬랙 오류가 발생하였습니다.";

    public SlackException() {
        super(ERROR_MESSAGE);
    }
}
