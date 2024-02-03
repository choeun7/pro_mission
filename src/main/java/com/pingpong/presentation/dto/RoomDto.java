package com.pingpong.presentation.dto;

import com.pingpong.domain.entity.Room;
import com.pingpong.domain.entity.RoomStatus;
import com.pingpong.domain.entity.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

    private Integer id;
    private String title;
    private Integer hostId;
    private RoomType roomType;
    private RoomStatus status;

    public static List<RoomDto> toDto(Page<Room> roomPage){
        List<RoomDto> roomDtoList = new ArrayList<>();
        for (Room room : roomPage) {
            RoomDto.RoomDtoBuilder builder = RoomDto.builder()
                    .id(room.getId())
                    .title(room.getTitle())
                    .hostId(room.getHost().getId())
                    .roomType(room.getRoomType())
                    .status(room.getStatus());

            roomDtoList.add(builder.build());
        }

        return roomDtoList;
    }
}
