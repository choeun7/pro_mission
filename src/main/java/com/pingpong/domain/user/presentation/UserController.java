package com.pingpong.domain.user.presentation;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.pingpong.domain.user.application.UserService;
import com.pingpong.domain.user.dto.request.FakerReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User API")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //초기화 후 Faker 유저 받아오기
    @Operation(summary = "초기화", description = "데이터를 초기화합니다.")
    @PostMapping("/init")
    public ResponseEntity<?> initialize(
            @Valid @RequestBody FakerReq fakerReq
    ) throws JsonProcessingException {
        return userService.initialize(fakerReq);
    }

    //유저 전체 조회
    @Operation(summary = "유저 조회", description = "모든 유저를 조회합니다. size와 page 모두 1부터 시작입니다.")
    @GetMapping("/user")
    public ResponseEntity<?> findAllUser(
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "page", defaultValue = "1") int page
            ) throws JsonProcessingException {
        return userService.findAllUser(size, page);
    }
}
