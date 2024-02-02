package com.pingpong.global.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.web.ErrorResponse;

@Getter
public class ApiResponse<T> {
    private Integer code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    @Builder
    public ApiResponse(Integer code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static <T> ApiResponse<T> res(StatusCode statusCode, T result) {
        return ApiResponse.<T>builder()
                .code(statusCode.getCode())
                .message(statusCode.getMessage())
                .result(result)
                .build();
    }

    public static <T> ApiResponse<T> res(StatusCode statusCode) {
        return res(statusCode, null);
    }
}