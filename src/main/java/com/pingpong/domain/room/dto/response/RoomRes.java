package com.pingpong.domain.room.dto.response;

import com.pingpong.domain.room.dto.RoomDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class RoomRes {

    private int totalElements;
    private int totalPages;
    private List<RoomDto> roomList;

    @Builder
    public RoomRes(int totalElements, int totalPages, List<RoomDto> roomList) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.roomList = roomList;
    }
}
