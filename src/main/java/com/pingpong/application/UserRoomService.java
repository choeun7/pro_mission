package com.pingpong.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pingpong.domain.entity.Room;
import com.pingpong.domain.entity.RoomStatus;
import com.pingpong.domain.entity.RoomType;
import com.pingpong.domain.repository.RoomRepository;
import com.pingpong.presentation.dto.request.UserRoomReq;
import com.pingpong.exception.situation.InvalidRoomException;
import com.pingpong.domain.entity.User;
import com.pingpong.domain.repository.UserRepository;
import com.pingpong.exception.situation.InvalidUserException;
import com.pingpong.domain.entity.TeamType;
import com.pingpong.domain.entity.UserRoom;
import com.pingpong.domain.repository.UserRoomRepository;
import com.pingpong.exception.situation.FullOppositeTeamException;
import com.pingpong.exception.situation.InvalidUserRoomException;
import com.pingpong.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse<?> changeTeam(Integer roomId, UserRoomReq userRoomReq) throws JsonProcessingException {

        Room room = roomRepository.findById(roomId).orElseThrow(InvalidRoomException::new);
        User user = userRepository.findById(userRoomReq.getUserId()).orElseThrow(InvalidUserException::new);
        UserRoom userRoom = userRoomRepository.findByUserAndRoom(user, room).orElseThrow(InvalidUserRoomException::new);

        // 각 팀의 현재 인원수
        int oppositeTeamMember = userRoomRepository.countByRoomAndTeamNot(room, userRoom.getTeam());
        int maxTeamMember = room.getRoomType() == RoomType.SINGLE ? 1 : 2;

        checkOppositeFull(oppositeTeamMember, maxTeamMember, room);

        userRoom.updateTeam();

        return ApiResponse.success();
    }


    //예외 체크
    private void checkOppositeFull(int oppositeTeamMember, int maxTeamMember, Room room) {
        if (oppositeTeamMember >= maxTeamMember) {
            //상대 팀이 이미 방 정원의 절반과 같을 때
            throw new FullOppositeTeamException();
        } else if (room.getStatus() != RoomStatus.WAIT) {
            //방이 대기(WAIT) 상태가 아닐 때
            throw new InvalidRoomException();
        }
    }
}
