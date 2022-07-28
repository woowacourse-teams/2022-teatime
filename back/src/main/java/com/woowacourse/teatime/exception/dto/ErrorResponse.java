package com.woowacourse.teatime.exception.dto;

public class ErrorResponse {

    private String message;

    private ErrorResponse() {
    }

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
