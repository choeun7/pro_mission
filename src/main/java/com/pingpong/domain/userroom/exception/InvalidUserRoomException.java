package com.pingpong.domain.userroom.exception;

public class InvalidUserRoomException extends RuntimeException {
    public InvalidUserRoomException() {
        super("유저가 방에 참가한 상태가 아닙니다.");
    }
}
