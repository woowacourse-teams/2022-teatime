package com.woowacourse.teatime.teatime.exception;

import com.woowacourse.teatime.exception.BadRequestException;

public class CannotPokeException extends BadRequestException {

    private static final String ERROR_MESSAGE = "콕 찌르기 요청을 거부한 사람에게 요청을 보낼 수 없습니다.";

    public CannotPokeException() {
        super(ERROR_MESSAGE);
    }
}
