package com.pingpong.global.payload;

import lombok.Getter;

@Getter
public enum StatusCode {

    OK(200, ResponseMessage.API_SUCCESS),
    INVALID(201, ResponseMessage.INVALID_REQ),
    SERVER_ERROR(500, ResponseMessage.INTERNAL_SERVER_ERROR),


    INVALID_PARAMETER(400, "잘못된 요청 데이터 입니다."),
    INVALID_REPRESENTATION(400, "잘못된 표현 입니다."),
    INVALID_FILE_PATH(400, "잘못된 파일 경로 입니다."),
    INVALID_OPTIONAL_ISPRESENT(400, "해당 값이 존재하지 않습니다."),
    INVALID_CHECK(400, "해당 값이 유효하지 않습니다."),
    INVALID_AUTHENTICATION(400, "잘못된 인증입니다.");

    private final int code;
    private final String message;

    StatusCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
}
