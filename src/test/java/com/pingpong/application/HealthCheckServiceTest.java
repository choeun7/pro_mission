package com.pingpong.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HealthCheckServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private HealthCheckService healthCheckService;

    @Test
    @DisplayName("API 헬스 체크 - 상태 UP")
    void whenApiStatusIsUp() {
        // Given
        Map<String, Object> upResponse = new HashMap<>();
        upResponse.put("status", "UP");
        when(restTemplate.getForObject(anyString(), any())).thenReturn(upResponse);

        // When
        Boolean result = healthCheckService.customHealthCheck();

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("API 헬스 체크 - 상태 DOWN")
    void whenApiStatusIsDown() {
        // Given
        Map<String, Object> downResponse = new HashMap<>();
        downResponse.put("status", "DOWN");
        when(restTemplate.getForObject(anyString(), any())).thenReturn(downResponse);

        // When
        Boolean result = healthCheckService.customHealthCheck();

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("API 헬스 체크 - 응답 없음")
    void whenApiDoesNotRespond() {
        // Given
        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);

        // When
        Boolean result = healthCheckService.customHealthCheck();

        // Then
        assertThat(result).isFalse();
    }
}
