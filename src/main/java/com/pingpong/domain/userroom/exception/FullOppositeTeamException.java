package com.pingpong.domain.userroom.exception;

public class FullOppositeTeamException extends RuntimeException{
    public FullOppositeTeamException() {
        super("상대 팀이 가득 찼습니다.");
    }
}
