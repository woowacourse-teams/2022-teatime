package com.woowacourse.teatime;

public class NotExistedCrewException extends UnAuthorizedException{

    private static final String DEFAULT_MESSAGE = "존재하지 않는 크루입니다.";

    public NotExistedCrewException() {
        super(DEFAULT_MESSAGE);
    }
}
