package com.pingpong.exception.situation;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException() {
        super("유저가 올바르지 않습니다.");
    }
}
