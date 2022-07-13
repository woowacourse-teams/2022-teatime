package com.woowacourse.teatime;

public class AlreadyExistedReservationException extends BadRequestException {
    private static final String DEFAULT_MESSAGE = "이미 예약이 되어 있습니다.";
    public AlreadyExistedReservationException() {
        super(DEFAULT_MESSAGE);
    }
}
