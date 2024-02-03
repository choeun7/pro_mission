package com.pingpong.exception.situation;

public class FullRoomException extends RuntimeException{
    public FullRoomException() {
        super("방의 인원이 가득 찼습니다.");
    }
}
