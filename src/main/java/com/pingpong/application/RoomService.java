package com.pingpong.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pingpong.domain.entity.Room;
import com.pingpong.domain.entity.RoomStatus;
import com.pingpong.domain.entity.RoomType;
import com.pingpong.domain.repository.RoomRepository;
import com.pingpong.presentation.dto.request.CreateRoomReq;
import com.pingpong.presentation.dto.request.UserRoomReq;
import com.pingpong.presentation.dto.response.RoomDetailRes;
import com.pingpong.presentation.dto.response.RoomRes;
import com.pingpong.exception.situation.*;
import com.pingpong.domain.entity.User;
import com.pingpong.domain.entity.UserStatus;
import com.pingpong.domain.repository.UserRepository;
import com.pingpong.domain.entity.TeamType;
import com.pingpong.domain.entity.UserRoom;
import com.pingpong.domain.repository.UserRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void createRoom(CreateRoomReq createRoomReq) throws JsonProcessingException {

        User user = userRepository.findById(createRoomReq.getUserId()).orElseThrow(InvalidUserException::new);
        checkUserState(user);
        checkUserAlreadyJoin(user);

        Room room = createRoomReq.toRoomEntity(user);
        roomRepository.save(room);

        UserRoom userRoom = createRoomReq.toUserRoomEntity(user, room);
        userRoomRepository.save(userRoom);
    }


    //방 전체 조회
    public RoomRes findAllRoom(int size, int page) throws JsonProcessingException {

        checkPageRange(size, page);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").ascending());

        return RoomRes.toDto(roomRepository.findAll(pageRequest));
    }

    //ID로 방 조회
    public RoomDetailRes findOneRoom(Integer roomId) throws JsonProcessingException {

        Room room = roomRepository.findById(roomId).orElseThrow(InvalidRoomException::new);
        return RoomDetailRes.toDto(room);
    }

    //방 참가
    @Transactional
    public void joinRoom(Integer roomId, UserRoomReq userRoomReq) throws JsonProcessingException {

        Room room = roomRepository.findById(roomId).orElseThrow(InvalidRoomException::new);
        User user = userRepository.findById(userRoomReq.getUserId()).orElseThrow(InvalidUserException::new);

        checkUserState(user);
        checkRoomIsFull(room);
        checkUserNotJoin(user, room);
        checkRoomState(room);

        // 각 팀의 현재 인원수
        int currentRedMember = userRoomRepository.countByRoomAndTeam(room, TeamType.RED);
        int maxTeamMember = room.getRoomType() == RoomType.SINGLE ? 1 : 2;
        TeamType teamType = TeamType.BLUE;

        if (currentRedMember < maxTeamMember) {
            teamType = TeamType.RED;
        }

        userRoomRepository.save(userRoomReq.toEntity(user, room, teamType));
    }

    //방 나가기
    @Transactional
    public void leaveRoom(Integer roomId, UserRoomReq userRoomReq) throws JsonProcessingException {

        Room room = roomRepository.findById(roomId).orElseThrow(InvalidRoomException::new);
        User user = userRepository.findById(userRoomReq.getUserId()).orElseThrow(InvalidUserException::new);
        UserRoom userRoom = userRoomRepository.findByUserAndRoom(user, room).orElseThrow(InvalidUserRoomException::new);

        checkRoomState(room);

        if (room.getHost() == user) {
            //나가는 유저가 방 호스트일 때
            room.updateStatus(RoomStatus.FINISH);
            userRoomRepository.deleteAll(userRoomRepository.findAllByRoom(room));
        } else {
            //나가는 유저가 일반 참가자일 때
            userRoomRepository.delete(userRoom);
        }
    }

    //게임 시작
    @Transactional
    public void startGame(Integer roomId, UserRoomReq userRoomReq) throws JsonProcessingException {

        Room room = roomRepository.findById(roomId).orElseThrow(InvalidRoomException::new);
        User user = userRepository.findById(userRoomReq.getUserId()).orElseThrow(InvalidUserException::new);
        userRoomRepository.findByUserAndRoom(user, room).orElseThrow(InvalidUserRoomException::new);

        checkHost(user, room);
        checkRoomIsNotFull(room);
        checkRoomState(room);

        room.updateStatus(RoomStatus.PROGRESS);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.schedule(() -> {
            log.info("1분 경과 게임 종료");
            finishGame(room);
            scheduler.shutdown();
        }, 1, TimeUnit.MINUTES);    //1분 후 FINISH로 변경
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

        userRoomRepository.deleteAll(userRoomRepository.findAllByRoom(room));
        room.updateStatus(RoomStatus.FINISH);
        roomRepository.save(room);
    }



    //예외 체크
    private void checkUserState(User user) {
        //생성 유저의 상태가 ACTIVE가 아니면
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new InvalidApiRequestException();
        }
    }
    private void checkUserAlreadyJoin(User user) {
        //생성 유저가 현재 참여한 방이 있다면
        if (userRoomRepository.existsByUser(user)) {
            throw new MultipleRoomJoinException();
        }
    }
    private void checkPageRange(int size, int page) {
        //페이지 설정이 둘 중 하나라도 1 이하이면
        if (size < 1 || page < 0) {
            throw new InvalidPageException();
        }
    }
    private void checkRoomState(Room room) {
        if (room.getStatus() != RoomStatus.WAIT) {
            //방이 대기상태가 아니면
            throw new InvalidApiRequestException();
        }
    }
    private void checkUserNotJoin(User user, Room room) {
        if (userRoomRepository.existsByUserAndRoom(user, room)) {
            //유저가 현재 참여한 방이 있다면
            throw new MultipleRoomJoinException();
        }
    }
    private void checkRoomIsFull(Room room) {
        if (isRoomFull(room)) {
            //참가하려는 방의 인원이 가득 찼다면
            throw new FullRoomException();
        }
    }
    private void checkHost(User user, Room room) {
        if (room.getHost() != user) {
            //해당 유저가 호스트가 아닐 때
            throw new InvalidUserException();
        }
    }
    private void checkRoomIsNotFull(Room room) {
        if (!isRoomFull(room)) {
            //방이 가득 찬 상태가 아닐 때
            throw new UnfilledRoomException();
        }
    }
}

