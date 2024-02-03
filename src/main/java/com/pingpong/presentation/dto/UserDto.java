package com.pingpong.presentation.dto;

import com.pingpong.domain.entity.User;
import com.pingpong.domain.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private Integer fakerId;
    private String name;
    private String email;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static List<UserDto> toDto(Page<User> userPage){
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

        return userDtoList;
    }
}
