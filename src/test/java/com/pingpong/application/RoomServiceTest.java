package com.pingpong.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pingpong.domain.entity.*;
import com.pingpong.domain.repository.RoomRepository;
import com.pingpong.presentation.dto.response.RoomDetailRes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    //테스트용 User 생성
    private User createUser() {
        return User.builder()
                .id(1)
                .name("Test User")
                .status(UserStatus.ACTIVE)
                .build();
    }

    //테스트용 Room 생성
    private Room createRoom(User user) {
        return Room.builder()
                .id(1)
                .title("Test Room")
                .host(user)
                .roomType(RoomType.SINGLE)
                .status(RoomStatus.WAIT)
                .build();
    }


    @Test
    @DisplayName("ID로 방 조회 성공")
    void findOneRoomSuccess() throws JsonProcessingException {
        // Given
        User user = createUser();
        when(roomRepository.findById(anyInt())).thenReturn(Optional.of(createRoom(user)));

        // When
        RoomDetailRes result = roomService.findOneRoom(1);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Room");
    }


}
