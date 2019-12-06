package com.karaoke.management.reponsitory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.karaoke.management.entity.RoomType;

@Repository("roomTypeRepository")
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
	RoomType findByTypeName(String roomName);
	List<RoomType> findByPrice(int price);
	boolean existsByTypeName(String typeName);
	
	@Query(value = "SELECT * FROM room_type WHERE room_type.id = ?1 and room_type.is_delete != true", nativeQuery = true)
	Optional<RoomType> findByTypeId(int roomTypeId);
	
	@Query(value = "SELECT * FROM room_type WHERE room_type.is_delete != true", nativeQuery = true)
	List<RoomType> findAllRoomType();
}
