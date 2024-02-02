package com.pingpong.domain.common;

import com.pingpong.global.payload.ApiResponse;
import com.pingpong.global.payload.StatusCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Tag(name = "HealthCheck", description = "HealthCheck API")
@RestController
@RequestMapping("/health")
public class CustomHealthCheckController {

    private final RestTemplate restTemplate;

    @Autowired
    public CustomHealthCheckController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<?> customHealth() {
        String url = "http://localhost:8080/actuator/health";
        Map<String, Object> originalResponse = restTemplate.getForObject(url, Map.class);

        if (originalResponse != null && "UP".equals(originalResponse.get("status"))) {
            ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.OK);
            return ResponseEntity.ok(apiResponse);
        } else {
            ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.SERVER_ERROR);
            return ResponseEntity.ok(apiResponse);
        }
    }
}
