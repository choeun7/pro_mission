package com.pingpong.domain.room.dto.request;

import com.pingpong.domain.room.domain.RoomType;
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
}
