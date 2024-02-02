package com.pingpong.domain.room.dto.response;

import com.pingpong.domain.room.domain.RoomStatus;
import com.pingpong.domain.room.domain.RoomType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoomDetailRes {

    private Integer id;
    private String title;
    private Integer hostId;
    private RoomType roomType;
    private RoomStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public RoomDetailRes(Integer id, String title, Integer hostId, RoomType roomType, RoomStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.hostId = hostId;
        this.roomType = roomType;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
