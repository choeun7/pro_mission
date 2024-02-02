package com.pingpong.domain.room.exception;

public class InvalidRoomException extends RuntimeException{
    public InvalidRoomException() {
        super("방이 올바르지 않습니다.");
    }
}
