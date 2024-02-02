package com.pingpong.domain.room.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pingpong.domain.room.application.RoomService;
import com.pingpong.domain.room.dto.request.CreateRoomReq;
import com.pingpong.domain.room.dto.request.UserRoomReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Room", description = "Room API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    //방 생성
    @Operation(summary = "방 생성", description = "방을 생성합니다.")
    @PostMapping
    public ResponseEntity<?> createRoom(
            @Valid @RequestBody CreateRoomReq createRoomReq
            ) throws JsonProcessingException {
        return roomService.createRoom(createRoomReq);
    }

    //방 전체 조회
    @Operation(summary = "방 조회", description = "모든 방을 조회합니다. size는 1부터, page는 0부터 시작합니다.")
    @GetMapping
    public ResponseEntity<?> findAllRoom(
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "page", defaultValue = "1") int page
    ) throws JsonProcessingException {
        return roomService.findAllRoom(size, page);
    }

    //ID로 방 하나 조회
    @Operation(summary = "ID로 방 조회", description = "roomID에 해당하는 방을 조회합니다.")
    @GetMapping("/{roomId}")
    public ResponseEntity<?> findRoom(
            @PathVariable(value = "roomId") Integer roomId
    ) throws JsonProcessingException {
        return roomService.findOneRoom(roomId);
    }

    //방 참가
    @Operation(summary = "방 참가", description = "방에 참가합니다.")
    @PutMapping("/attention/{roomId}")
    public ResponseEntity<?> joinRoom(
            @PathVariable(value = "roomId") Integer roomId,
            @Valid @RequestBody UserRoomReq userRoomReq
            ) throws JsonProcessingException {
        return roomService.joinRoom(roomId, userRoomReq);
    }

    //방 나가기
    @Operation(summary = "방 나가기", description = "방을 나갑니다.")
    @PostMapping("/out/{roomId}")
    public ResponseEntity<?> leaveRoom(
            @PathVariable(value = "roomId") Integer roomId,
            @Valid @RequestBody UserRoomReq userRoomReq
    ) throws JsonProcessingException {
        return roomService.leaveRoom(roomId, userRoomReq);
    }

    //게임 시작
    @Operation(summary = "게임 시작", description = "게임을 시작합니다.")
    @PutMapping("/start/{roomId}")
    public ResponseEntity<?> startGame(
            @PathVariable(value = "roomId") Integer roomId,
            @Valid @RequestBody UserRoomReq userRoomReq
    ) throws JsonProcessingException {
        return roomService.startGame(roomId, userRoomReq);
    }
}
