package com.karaoke.management.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karaoke.management.entity.Room;
import com.karaoke.management.entity.RoomType;

@Repository("roomRepository")
public interface RoomRepository extends JpaRepository<Room, Integer> {
	Room findByRoomId(int roomId);
	Room findByRoomName(String roomName);
	List<Room> findByRoomType(RoomType roomType);
	List<Room> findByStatus(int status);
	boolean existsByRoomName(String roomName);
	boolean existsByRoomIdAndStatus(int roomId, int status);
	boolean existsByRoomType(RoomType roomType);
}
