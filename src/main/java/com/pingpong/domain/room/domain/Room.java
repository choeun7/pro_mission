package com.pingpong.domain.room.domain;

import com.pingpong.domain.common.BaseEntity;
import com.pingpong.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "room")
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private User host;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @Builder
    public Room(String title, User host, RoomType roomType, RoomStatus status) {
        this.title = title;
        this.host = host;
        this.roomType = roomType;
        this.status = status;
    }

    public void updateStatus(RoomStatus status) {
        this.status = status;
    }
}
