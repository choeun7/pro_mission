package com.pingpong.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pingpong.global.payload.ApiResponse;
import com.pingpong.presentation.dto.request.UserRoomReq;
import com.pingpong.application.UserRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Team", description = "Team API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class UserRoomController {

    private final UserRoomService userRoomService;

    //팀 변경
    @Operation(summary = "팀 변경", description = "팀을 변합합니다.")
    @PutMapping("/{roomId}")
    public ApiResponse<?> changeTeam(
            @PathVariable(value = "roomId") Integer roomId,
            @Valid @RequestBody UserRoomReq userRoomReq
    ) throws JsonProcessingException {
        return userRoomService.changeTeam(roomId, userRoomReq);
    }
}
