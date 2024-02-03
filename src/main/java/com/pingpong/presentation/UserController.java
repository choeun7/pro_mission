package com.pingpong.presentation;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.pingpong.application.UserService;
import com.pingpong.global.payload.ApiResponse;
import com.pingpong.presentation.dto.request.FakerReq;
import io.swagger.v3.oas.annotations.Operation;
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
    public ApiResponse<?> initialize(
            @Valid @RequestBody FakerReq fakerReq
    ) throws JsonProcessingException {
        userService.initialize(fakerReq);
        return ApiResponse.success();
    }

    //유저 전체 조회
    @Operation(summary = "유저 조회", description = "모든 유저를 조회합니다. size는 1, page는 0이 최솟값입니다.")
    @GetMapping("/user")
    public ApiResponse<?> findAllUser(
            @RequestParam(name = "size", defaultValue = "3") int size,
            @RequestParam(name = "page", defaultValue = "0") int page
            ) throws JsonProcessingException {
        return ApiResponse.success(userService.findAllUser(size, page));
    }
}
