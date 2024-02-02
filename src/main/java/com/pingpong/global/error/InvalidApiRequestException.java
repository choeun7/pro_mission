package com.pingpong.global.error;

public class InvalidApiRequestException extends RuntimeException {

    public InvalidApiRequestException() {
        super("올바르지 않은 API 요청입니다. 201 응답입니다.");
    }
}

