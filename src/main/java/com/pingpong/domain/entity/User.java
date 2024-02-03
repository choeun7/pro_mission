package com.pingpong.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer fakerId;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Builder
    public User(Integer id, Integer fakerId, String name, String email, UserStatus status) {
        this.id = id;
        this.fakerId = fakerId;
        this.name = name;
        this.email = email;
        this.status = status;
    }
}
