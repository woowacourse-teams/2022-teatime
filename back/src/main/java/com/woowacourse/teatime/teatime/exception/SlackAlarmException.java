package com.woowacourse.teatime.teatime.exception;

import com.woowacourse.teatime.exception.SlackException;

public class SlackAlarmException extends SlackException {

    private static final String ERROR_MESSAGE = "슬랙 알림 중 오류가 발생하였습니다.";

    public SlackAlarmException() {
        super(ERROR_MESSAGE);
    }
}
