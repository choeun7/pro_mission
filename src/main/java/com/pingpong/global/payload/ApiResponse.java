package com.pingpong.global.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.web.ErrorResponse;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class ApiResponse<T> {
    private Integer code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;


    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), result);
    }

    public static <T> ApiResponse<T> success() {
        return success(null);
    }

    public static <T> ApiResponse<T> failed() {
        return new ApiResponse<>(StatusCode.OK.getCode(), StatusCode.FAILED.getMessage(), null);
    }

    public static <T> ApiResponse<T> invalid() {
        return new ApiResponse<>(StatusCode.INVALID.getCode(), StatusCode.INVALID.getMessage(), null);
    }

    public static <T> ApiResponse<T> error() {
        return new ApiResponse<>(StatusCode.SERVER_ERROR.getCode(), StatusCode.SERVER_ERROR.getMessage(), null);
    }
}