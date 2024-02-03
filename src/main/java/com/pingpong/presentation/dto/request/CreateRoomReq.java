package com.pingpong.presentation.dto.request;

import com.pingpong.domain.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "CreateRoomRequest")
public class CreateRoomReq {

    @Schema(description = "생성자ID", example = "1")
    private Integer userId;

    @Schema(description = "방 종류", example = "SINGLE")
    private RoomType roomType;

    @Schema(description = "제목", example = "테스트룸")
    private String title;

    public Room toRoomEntity(User user) {
        return Room.builder()
                .roomType(this.getRoomType())
                .status(RoomStatus.WAIT)
                .title(this.getTitle())
                .host(user)
                .build();
    }

    public UserRoom toUserRoomEntity(User user, Room room) {
        return UserRoom.builder()
                .user(user)
                .room(room)
                .team(TeamType.RED)
                .build();
    }
}
