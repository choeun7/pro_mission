package com.pingpong.domain.user.dto.response;

import com.pingpong.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class UserRes {

    private int totalElements;
    private int totalPages;
    private List<UserDto> userList;

    @Builder
    public UserRes(int totalElements, int totalPages, List<UserDto> userList) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.userList = userList;
    }
}
