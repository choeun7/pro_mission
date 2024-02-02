package com.pingpong.domain.room.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pingpong.domain.room.domain.Room;
import com.pingpong.domain.room.domain.RoomStatus;
import com.pingpong.domain.room.domain.RoomType;
import com.pingpong.domain.room.domain.repository.RoomRepository;
import com.pingpong.domain.room.dto.RoomDto;
import com.pingpong.domain.room.dto.request.CreateRoomReq;
import com.pingpong.domain.room.dto.request.UserRoomReq;
import com.pingpong.domain.room.dto.response.RoomDetailRes;
import com.pingpong.domain.room.dto.response.RoomRes;
import com.pingpong.domain.room.exception.InvalidRoomException;
import com.pingpong.domain.user.domain.User;
import com.pingpong.domain.user.domain.UserStatus;
import com.pingpong.domain.user.domain.repository.UserRepository;
import com.pingpong.domain.user.exception.InvalidPageException;
import com.pingpong.domain.user.exception.InvalidUserException;
import com.pingpong.domain.userroom.domain.TeamType;
import com.pingpong.domain.userroom.domain.UserRoom;
import com.pingpong.domain.userroom.domain.repository.UserRoomRepository;
import com.pingpong.domain.userroom.exception.*;
import com.pingpong.global.error.InvalidApiRequestException;
import com.pingpong.global.payload.ApiResponse;
import com.pingpong.global.payload.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;

    //방 생성
    @Transactional
    public ResponseEntity<?> createRoom(CreateRoomReq createRoomReq) throws JsonProcessingException {

        User user = userRepository.findById(createRoomReq.getUserId()).orElseThrow(InvalidUserException::new);
        //생성 유저의 상태가 ACTIVE가 아니면 201 응답
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new InvalidApiRequestException();
        }
        //생성 유저가 현재 참여한 방이 있다면, 201 응답

        Room room = Room.builder()
                .roomType(createRoomReq.getRoomType())
                .status(RoomStatus.WAIT)
                .title(createRoomReq.getTitle())
                .host(user)
                .build();

        roomRepository.save(room);

        UserRoom userRoom = UserRoom.builder()
                .user(user)
                .room(room)
                .team(TeamType.RED)
                .build();

        userRoomRepository.save(userRoom);

        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.OK);
        return ResponseEntity.ok(apiResponse);
    }


    //방 전체 조회
    public ResponseEntity<?> findAllRoom(int size, int page) throws JsonProcessingException {

        //페이지 설정이 둘 중 하나라도 1 이하이면 500 상태
        if (size < 1 || page < 0) {
            throw new InvalidPageException();
        }

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").ascending());

        Page<Room> roomPage = roomRepository.findAll(pageRequest);

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

        RoomRes roomRes = RoomRes.builder()
                .totalElements((int)roomPage.getTotalElements())
                .totalPages(roomPage.getTotalPages())
                .roomList(roomDtoList)
                .build();

        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.OK, roomRes);
        return ResponseEntity.ok(apiResponse);
    }

    //ID로 방 조회
    public ResponseEntity<?> findOneRoom(Integer roomId) throws JsonProcessingException {

        Room room = roomRepository.findById(roomId).orElseThrow(InvalidRoomException::new);

        RoomDetailRes roomDetailRes = RoomDetailRes.builder()
                .id(room.getId())
                .title(room.getTitle())
                .hostId(room.getHost().getId())
                .roomType(room.getRoomType())
                .status(room.getStatus())
                .createdAt(room.getCreatedAt())
                .updatedAt(room.getUpdatedAt())
                .build();

        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.OK, roomDetailRes);
        return ResponseEntity.ok(apiResponse);
    }

    //방 참가
    @Transactional
    public ResponseEntity<?> joinRoom(Integer roomId, UserRoomReq userRoomReq) throws JsonProcessingException {

        Room room = roomRepository.findById(roomId).orElseThrow(InvalidRoomException::new);
        User user = userRepository.findById(userRoomReq.getUserId()).orElseThrow(InvalidUserException::new);

        if (room.getStatus() != RoomStatus.WAIT || user.getStatus() != UserStatus.ACTIVE) {
            //방이 대기상태가 아니거나, 유저가 대기상태가 아니면
            throw new InvalidApiRequestException();
        } else if (userRoomRepository.existsByUserAndRoom(user, room)) {
            //유저가 현재 참여한 방이 있다면
            throw new MultipleRoomJoinException();
        } else if (isRoomFull(room)) {
            //참가하려는 방의 인원이 가득 찼다면
            throw new FullRoomException();
        }

        // 각 팀의 현재 인원수
        int currentRedMember = userRoomRepository.countByRoomAndTeam(room, TeamType.RED);
        int maxTeamMember = room.getRoomType() == RoomType.SINGLE ? 1 : 2;
        TeamType teamType = TeamType.BLUE;

        if (currentRedMember < maxTeamMember) {
            teamType = TeamType.RED;
        }

        UserRoom userRoom = UserRoom.builder()
                .user(user)
                .room(room)
                .team(teamType)
                .build();

        userRoomRepository.save(userRoom);

        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.OK);
        return ResponseEntity.ok(apiResponse);
    }

    //방 나가기
    @Transactional
    public ResponseEntity<?> leaveRoom(Integer roomId, UserRoomReq userRoomReq) throws JsonProcessingException {

        Room room = roomRepository.findById(roomId).orElseThrow(InvalidRoomException::new);
        User user = userRepository.findById(userRoomReq.getUserId()).orElseThrow(InvalidUserException::new);
        UserRoom userRoom = userRoomRepository.findByUserAndRoom(user, room).orElseThrow(InvalidUserRoomException::new);

        if (room.getStatus() != RoomStatus.WAIT) {
            //시작(PROCESS) 또는 끝난(FINISH) 상태일 때
            throw new InvalidApiRequestException();
        }

        if (room.getHost() == user) {
            //나가는 유저가 방 호스트일 때
            room.updateStatus(RoomStatus.FINISH);
            List<UserRoom> userRoomList = userRoomRepository.findAllByRoom(room);
            userRoomRepository.deleteAll(userRoomList);
        } else {
            //나가는 유저가 일반 참가자일 때
            userRoomRepository.delete(userRoom);
        }

        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.OK);
        return ResponseEntity.ok(apiResponse);
    }

    //게임 시작
    @Transactional
    public ResponseEntity<?> startGame(Integer roomId, UserRoomReq userRoomReq) throws JsonProcessingException {

        Room room = roomRepository.findById(roomId).orElseThrow(InvalidRoomException::new);
        User user = userRepository.findById(userRoomReq.getUserId()).orElseThrow(InvalidUserException::new);
        UserRoom userRoom = userRoomRepository.findByUserAndRoom(user, room).orElseThrow(InvalidUserRoomException::new);

        if (room.getHost() != user) {
            //해당 유저가 호스트가 아닐 때
            throw new InvalidUserException();
        } else if (!isRoomFull(room)){
            //방이 가득 찬 상태가 아닐 때
            throw new UnfilledRoomException();
        } else if (room.getStatus() != RoomStatus.WAIT) {
            //방의 상태가 대기(Wait)가 아닐 때
            throw new InvalidRoomException();
        }

        room.updateStatus(RoomStatus.PROGRESS);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.schedule(() -> {
            log.info("1분 경과 게임 종료");
            finishGame(room);
            scheduler.shutdown();
        }, 1, TimeUnit.MINUTES);    //1분 후 FINISH로 변경

        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.OK);
        return ResponseEntity.ok(apiResponse);
    }

    //방이 가득 찼는지 확인
    private Boolean isRoomFull(Room room) {

        int maxRoomMember = room.getRoomType() == RoomType.SINGLE ? 2 : 4;
        if (userRoomRepository.countByRoom(room) >= maxRoomMember) {
            return true;
        }
        return false;
    }

    //게임 종료 상태로 변경
    @Transactional
    protected void finishGame(Room room) {
        room.updateStatus(RoomStatus.FINISH);
        roomRepository.save(room);
    }
}
