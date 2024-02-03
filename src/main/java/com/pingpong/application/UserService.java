package com.pingpong.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pingpong.domain.repository.RoomRepository;
import com.pingpong.domain.entity.User;
import com.pingpong.domain.entity.UserStatus;
import com.pingpong.domain.repository.UserRepository;
import com.pingpong.presentation.dto.request.FakerReq;
import com.pingpong.presentation.dto.response.UserRes;
import com.pingpong.exception.situation.InvalidPageException;
import com.pingpong.domain.repository.UserRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Transactional
    public void initialize(FakerReq fakerReq) throws JsonProcessingException {
        // 모든 정보 삭제
        userRepository.deleteAll();
        roomRepository.deleteAll();
        userRoomRepository.deleteAll();

        String url = String.format("https://fakerapi.it/api/v1/users?_seed=%d&_quantity=%d&_locale=ko_KR", fakerReq.getSeed(), fakerReq.getQuantity());
        String jsonResponse = restTemplate.getForObject(url, String.class);

        JsonNode usersData = objectMapper.readTree(jsonResponse).get("data");

        // 데이터 저장
        if (usersData.isArray()) {
            for (JsonNode userNode : usersData) {
                Integer fakerId = userNode.get("id").asInt();

                UserStatus status;
                if (fakerId <= 30) {
                    status = UserStatus.ACTIVE;
                } else if (fakerId <= 60) {
                    status = UserStatus.WAIT;
                } else {
                    status = UserStatus.NON_ACTIVE;
                }

                User user = User.builder()
                        .fakerId(fakerId)
                        .name(userNode.get("username").asText())
                        .email(userNode.get("email").asText())
                        .status(status)
                        .build();

                userRepository.save(user);
            }
        }
    }

    // 유저 전체 조회
    public UserRes findAllUser(int size, int page) throws JsonProcessingException {

        if (size < 1 || page < 0) {
            throw new InvalidPageException();
        }

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").ascending());

        return UserRes.toDto(userRepository.findAll(pageRequest));

    }
}

