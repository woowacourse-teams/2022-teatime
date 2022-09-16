package com.woowacourse.teatime.auth.exception;

import com.woowacourse.teatime.exception.SlackException;

public class SlackLoginException extends SlackException {

    private static final String ERROR_MESSAGE = "슬랙 로그인 중 오류가 발생하였습니다.";

    public SlackLoginException() {
        super(ERROR_MESSAGE);
    }
}
