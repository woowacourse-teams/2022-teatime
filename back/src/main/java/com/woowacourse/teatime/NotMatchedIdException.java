package com.woowacourse.teatime;

public class NotMatchedIdException extends BadRequestException {

    public NotMatchedIdException(String message) {
        super(message);
    }
}
