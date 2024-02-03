package com.pingpong.exception.situation;

public class InvalidPageException extends RuntimeException {
    public InvalidPageException() {
        super("가능한 페이지 범위에서 벗어났습니다.");
    }
}
