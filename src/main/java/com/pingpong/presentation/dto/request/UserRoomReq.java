package com.pingpong.presentation.dto.request;

import com.pingpong.domain.entity.Room;
import com.pingpong.domain.entity.TeamType;
import com.pingpong.domain.entity.User;
import com.pingpong.domain.entity.UserRoom;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "JoinRoomRequest")
@AllArgsConstructor
@NoArgsConstructor
public class UserRoomReq {

    @Schema(description = "유저ID", example = "1")
    private Integer userId;

    public UserRoom toEntity(User user, Room room, TeamType teamType){
        return UserRoom.builder()
                .user(user)
                .room(room)
                .team(teamType)
                .build();
    }

}
