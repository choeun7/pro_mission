package com.pingpong.domain.room.dto;

import com.pingpong.domain.room.domain.RoomStatus;
import com.pingpong.domain.room.domain.RoomType;
import com.pingpong.domain.user.domain.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoomDto {

    private Integer id;
    private String title;
    private Integer hostId;
    private RoomType roomType;
    private RoomStatus status;

    @Builder
    public RoomDto(Integer id, String title, Integer hostId, RoomType roomType, RoomStatus status) {
        this.id = id;
        this.title = title;
        this.hostId = hostId;
        this.roomType = roomType;
        this.status = status;
    }
}
