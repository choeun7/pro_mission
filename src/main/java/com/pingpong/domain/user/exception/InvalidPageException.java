package com.pingpong.domain.user.exception;

public class InvalidPageException extends RuntimeException {
    public InvalidPageException() {
        super("가능한 페이지 범위에서 벗어났습니다.");
    }
}
