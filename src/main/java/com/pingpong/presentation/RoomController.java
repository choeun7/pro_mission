package com.pingpong.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pingpong.application.RoomService;
import com.pingpong.global.payload.ApiResponse;
import com.pingpong.presentation.dto.request.CreateRoomReq;
import com.pingpong.presentation.dto.request.UserRoomReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse<?> createRoom(
            @Valid @RequestBody CreateRoomReq createRoomReq
            ) throws JsonProcessingException {
        roomService.createRoom(createRoomReq);
        return ApiResponse.success();
    }

    //방 전체 조회
    @Operation(summary = "방 조회", description = "모든 방을 조회합니다. size는 1, page는 0이 최솟값입니다.")
    @GetMapping
    public ApiResponse<?> findAllRoom(
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "page", defaultValue = "0") int page
    ) throws JsonProcessingException {
        return ApiResponse.success(roomService.findAllRoom(size, page));
    }

    //ID로 방 하나 조회
    @Operation(summary = "ID로 방 조회", description = "roomID에 해당하는 방을 조회합니다.")
    @GetMapping("/{roomId}")
    public ApiResponse<?> findRoom(
            @PathVariable(value = "roomId") Integer roomId
    ) throws JsonProcessingException {
        return ApiResponse.success(roomService.findOneRoom(roomId));
    }

    //방 참가
    @Operation(summary = "방 참가", description = "방에 참가합니다.")
    @PutMapping("/attention/{roomId}")
    public ApiResponse<?> joinRoom(
            @PathVariable(value = "roomId") Integer roomId,
            @Valid @RequestBody UserRoomReq userRoomReq
            ) throws JsonProcessingException {
        roomService.joinRoom(roomId, userRoomReq);
        return ApiResponse.success();
    }

    //방 나가기
    @Operation(summary = "방 나가기", description = "방을 나갑니다.")
    @PostMapping("/out/{roomId}")
    public ApiResponse<?> leaveRoom(
            @PathVariable(value = "roomId") Integer roomId,
            @Valid @RequestBody UserRoomReq userRoomReq
    ) throws JsonProcessingException {
        roomService.leaveRoom(roomId, userRoomReq);
        return ApiResponse.success();
    }

    //게임 시작
    @Operation(summary = "게임 시작", description = "게임을 시작합니다.")
    @PutMapping("/start/{roomId}")
    public ApiResponse<?> startGame(
            @PathVariable(value = "roomId") Integer roomId,
            @Valid @RequestBody UserRoomReq userRoomReq
    ) throws JsonProcessingException {
        roomService.startGame(roomId, userRoomReq);
        return ApiResponse.success();
    }
}
