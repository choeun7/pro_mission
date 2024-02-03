package com.pingpong.presentation.dto.response;

import com.pingpong.domain.entity.Room;
import com.pingpong.presentation.dto.RoomDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomRes {

    private int totalElements;
    private int totalPages;
    private List<RoomDto> roomList;

    public static RoomRes toDto(Page<Room> roomPage) {
        return RoomRes.builder()
                .totalElements((int)roomPage.getTotalElements())
                .totalPages(roomPage.getTotalPages())
                .roomList(RoomDto.toDto(roomPage))
                .build();
    }
}
