package com.pingpong.domain.userroom.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pingpong.domain.room.domain.Room;
import com.pingpong.domain.room.domain.RoomStatus;
import com.pingpong.domain.room.domain.RoomType;
import com.pingpong.domain.room.domain.repository.RoomRepository;
import com.pingpong.domain.room.dto.request.UserRoomReq;
import com.pingpong.domain.room.exception.InvalidRoomException;
import com.pingpong.domain.user.domain.User;
import com.pingpong.domain.user.domain.repository.UserRepository;
import com.pingpong.domain.user.exception.InvalidUserException;
import com.pingpong.domain.userroom.domain.TeamType;
import com.pingpong.domain.userroom.domain.UserRoom;
import com.pingpong.domain.userroom.domain.repository.UserRoomRepository;
import com.pingpong.domain.userroom.exception.FullOppositeTeamException;
import com.pingpong.domain.userroom.exception.InvalidUserRoomException;
import com.pingpong.global.payload.ApiResponse;
import com.pingpong.global.payload.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserRoomService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;

    //팀 변경
    @Transactional
    public ResponseEntity<?> changeTeam(Integer roomId, UserRoomReq userRoomReq) throws JsonProcessingException {

        Room room = roomRepository.findById(roomId).orElseThrow(InvalidRoomException::new);
        User user = userRepository.findById(userRoomReq.getUserId()).orElseThrow(InvalidUserException::new);
        UserRoom userRoom = userRoomRepository.findByUserAndRoom(user, room).orElseThrow(InvalidUserRoomException::new);

        // 각 팀의 현재 인원수
        int oppositeTeamMember = userRoomRepository.countByRoomAndTeamNot(room, userRoom.getTeam());
        int maxTeamMember = room.getRoomType() == RoomType.SINGLE ? 1 : 2;
        TeamType teamType = TeamType.BLUE;

        if (oppositeTeamMember >= maxTeamMember) {
            //상대 팀이 이미 방 정원의 절반과 같을 때
            throw new FullOppositeTeamException();
        } else if (room.getStatus() != RoomStatus.WAIT) {
            //방이 대기(WAIT) 상태가 아닐 때
            throw new InvalidRoomException();
        }

        userRoom.updateTeam();

        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.OK);
        return ResponseEntity.ok(apiResponse);
    }
}
