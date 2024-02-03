package com.pingpong.presentation;

import com.pingpong.application.HealthCheckService;
import com.pingpong.global.payload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "HealthCheck", description = "HealthCheck API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/health")
public class CustomHealthCheckController {

    private final HealthCheckService healthCheckService;

    //헬스체크
    @Operation(summary = "헬스체크", description = "서버의 상태를 체크합니다.")
    @GetMapping
    public ApiResponse<?> customHealth() {
        if (healthCheckService.customHealthCheck()) {
            return ApiResponse.success();
        }
        return ApiResponse.failed();
    }
}
