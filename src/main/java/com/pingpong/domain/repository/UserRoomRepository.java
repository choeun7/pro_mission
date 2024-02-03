package com.pingpong.domain.repository;

import com.pingpong.domain.entity.Room;
import com.pingpong.domain.entity.User;
import com.pingpong.domain.entity.TeamType;
import com.pingpong.domain.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {

    Boolean existsByUserAndRoom(User user, Room room);

    Boolean existsByUser(User user);

    Optional<UserRoom> findByUserAndRoom(User user, Room room);

    int countByRoom(Room room);

    int countByRoomAndTeam(Room room, TeamType teamType);

    int countByRoomAndTeamNot(Room room, TeamType teamType);

    List<UserRoom> findAllByRoom(Room room);
}
