package com.pingpong.domain.room.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "JoinRoomRequest")
public class UserRoomReq {

    @Schema(description = "유저ID", example = "1")
    private Integer userId;

}
