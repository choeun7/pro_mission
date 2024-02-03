package com.pingpong.presentation.dto.response;

import com.pingpong.domain.entity.User;
import com.pingpong.presentation.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRes {

    private int totalElements;
    private int totalPages;
    private List<UserDto> userList;

    public static UserRes toDto(Page<User> userPage) {
        return UserRes.builder()
                .totalElements((int)userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .userList(UserDto.toDto(userPage))
                .build();
    }
}
