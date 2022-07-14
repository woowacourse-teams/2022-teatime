package com.woowacourse.teatime;

public class AlreadyReservedException extends BadRequestException {
    private static final String DEFAULT_MESSAGE = "이미 예약이 되어 있습니다.";
    public AlreadyReservedException() {
        super(DEFAULT_MESSAGE);
    }
}
