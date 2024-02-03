package com.pingpong.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HealthCheckService {

    private final RestTemplate restTemplate;

    //api 헬스 체크
    public Boolean customHealthCheck(){
        String url = "http://localhost:8080/actuator/health";
        Map<String, Object> originalResponse = restTemplate.getForObject(url, Map.class);

        if (originalResponse != null && "UP".equals(originalResponse.get("status"))) {
            return true;
        } else {
            return false;
        }
    }
}
