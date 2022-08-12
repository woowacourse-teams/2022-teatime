package com.woowacourse.teatime.auth.exception;

import com.woowacourse.teatime.exception.BadGatewayException;

public class SlackException extends BadGatewayException {

    private static final String ERROR_MESSAGE = "슬랙 오류가 발생하였습니다.";

    public SlackException() {
        super(ERROR_MESSAGE);
    }
}
