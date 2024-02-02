package com.pingpong.domain.userroom.domain.repository;

import com.pingpong.domain.room.domain.Room;
import com.pingpong.domain.user.domain.User;
import com.pingpong.domain.userroom.domain.TeamType;
import com.pingpong.domain.userroom.domain.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {

    Boolean existsByUserAndRoom(User user, Room room);

    Optional<UserRoom> findByUserAndRoom(User user, Room room);

    int countByRoom(Room room);

    int countByRoomAndTeam(Room room, TeamType teamType);

    int countByRoomAndTeamNot(Room room, TeamType teamType);

    List<UserRoom> findAllByRoom(Room room);
}
