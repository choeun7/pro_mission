package com.pingpong.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pingpong.domain.entity.User;
import com.pingpong.domain.repository.UserRepository;
import com.pingpong.exception.situation.InvalidPageException;
import com.pingpong.presentation.dto.response.UserRes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    @DisplayName("조회 테스트")
    public void userResTest() throws JsonProcessingException {
        // Given
        List<User> mockUserList = List.of(
                User.builder().name("테스트 유저1").build(),
                User.builder().name("테스트 유저2").build(),
                User.builder().name("테스트 유저3").build()
        );
        Page<User> mockUserPage = new PageImpl<>(mockUserList);
        when(userRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(mockUserPage);

        // When
        UserRes userRes = userService.findAllUser(10,0);

        // Then
        assertThat(userRes.getUserList().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("유저 전체 조회 - 페이지 설정이 잘못되었을 때 예외 발생")
    void findAllUser() {
        // Given
        int size = 0;
        int page = 0;

        // When & Then
        assertThatThrownBy(() -> userService.findAllUser(size, page))
                .isInstanceOf(InvalidPageException.class);
    }
}
