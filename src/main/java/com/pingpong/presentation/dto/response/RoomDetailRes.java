package com.pingpong.presentation.dto.response;

import com.pingpong.domain.entity.Room;
import com.pingpong.domain.entity.RoomStatus;
import com.pingpong.domain.entity.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailRes {

    private Integer id;
    private String title;
    private Integer hostId;
    private RoomType roomType;
    private RoomStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RoomDetailRes toDto(Room room) {
        return RoomDetailRes.builder()
                .id(room.getId())
                .title(room.getTitle())
                .hostId(room.getHost().getId())
                .roomType(room.getRoomType())
                .status(room.getStatus())
                .createdAt(room.getCreatedAt())
                .updatedAt(room.getUpdatedAt())
                .build();
    }
}
