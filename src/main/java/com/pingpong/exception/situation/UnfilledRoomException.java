package com.pingpong.exception.situation;

public class UnfilledRoomException extends RuntimeException{
    public UnfilledRoomException() {
        super("방의 인원이 가득 차지 않았습니다.");
    }
}
