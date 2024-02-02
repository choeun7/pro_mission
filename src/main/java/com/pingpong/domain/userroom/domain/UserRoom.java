package com.pingpong.domain.userroom.domain;

import com.pingpong.domain.common.BaseEntity;
import com.pingpong.domain.room.domain.Room;
import com.pingpong.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user_room")
public class UserRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Enumerated(EnumType.STRING)
    private TeamType team;

    @Builder
    public UserRoom(User user, Room room, TeamType team) {
        this.user = user;
        this.room = room;
        this.team = team;
    }

    public void updateTeam() {
        if (this.getTeam() == TeamType.RED) {
            this.team = TeamType.BLUE;
        } else {
            this.team = TeamType.RED;
        }
    }
}
