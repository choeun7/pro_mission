package com.pingpong.domain.user.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pingpong.domain.room.domain.repository.RoomRepository;
import com.pingpong.domain.user.domain.User;
import com.pingpong.domain.user.domain.UserStatus;
import com.pingpong.domain.user.domain.repository.UserRepository;
import com.pingpong.domain.user.dto.UserDto;
import com.pingpong.domain.user.dto.request.FakerReq;
import com.pingpong.domain.user.dto.response.UserRes;
import com.pingpong.domain.user.exception.InvalidPageException;
import com.pingpong.domain.userroom.domain.repository.UserRoomRepository;
import com.pingpong.global.error.InvalidApiRequestException;
import com.pingpong.global.payload.ApiResponse;
import com.pingpong.global.payload.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<?> initialize(FakerReq fakerReq) throws JsonProcessingException {
        // 모든 회원 정보 및 방 정보 삭제
        userRepository.deleteAll();
        roomRepository.deleteAll();
        userRoomRepository.deleteAll();

        // Faker API 호출
        String url = String.format("https://fakerapi.it/api/v1/users?_seed=%d&_quantity=%d&_locale=ko_KR", fakerReq.getSeed(), fakerReq.getQuantity());
        String jsonResponse = restTemplate.getForObject(url, String.class);

        // 응답 데이터 파싱
        JsonNode usersData = objectMapper.readTree(jsonResponse).get("data");

        // 데이터 저장
        if (usersData.isArray()) {
            for (JsonNode userNode : usersData) {
                Integer fakerId = userNode.get("id").asInt();

                // 상태 설정
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

        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.OK);

        return ResponseEntity.ok(apiResponse);
    }

    // 유저 전체 조회
    public ResponseEntity<?> findAllUser(int size, int page) throws JsonProcessingException {

        //페이지 설정이 둘 중 하나라도 1 이하이면 500 상태
        if (size < 1 || page < 1) {
            throw new InvalidPageException();
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("id").ascending());

        Page<User> userPage = userRepository.findAll(pageRequest);

        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userPage) {
            UserDto.UserDtoBuilder builder = UserDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .fakerId(user.getFakerId())
                    .email(user.getEmail())
                    .status(user.getStatus())
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt());

            userDtoList.add(builder.build());
        }

        UserRes userRes = UserRes.builder()
                .totalElements((int)userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .userList(userDtoList)
                .build();

        ApiResponse<?> apiResponse = ApiResponse.res(StatusCode.OK, userRes);

        return ResponseEntity.ok(apiResponse);

    }
}

