package com.karaoke.management.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karaoke.management.entity.RoomType;

@Repository("roomTypeRepository")
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
	RoomType findByTypeId(int roomTypeId);
	RoomType findByTypeName(String roomName);
	List<RoomType> findByPrice(int price);
	boolean existsByTypeName(String typeName);
}
