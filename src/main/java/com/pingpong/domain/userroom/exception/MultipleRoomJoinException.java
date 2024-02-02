package com.pingpong.domain.userroom.exception;

public class MultipleRoomJoinException extends RuntimeException {
    public MultipleRoomJoinException() {
        super("유저가 이미 방에 참여한 상태입니다.");
    }
}
