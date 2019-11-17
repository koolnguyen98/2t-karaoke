package com.karaoke.management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.karaoke.management.api.response.ApiResponse;
import com.karaoke.management.entity.Food;
import com.karaoke.management.entity.Room;
import com.karaoke.management.entity.RoomType;
import com.karaoke.management.entity.UserAccount;
import com.karaoke.management.reponsitory.FoodRepository;
import com.karaoke.management.reponsitory.RoomRepository;
import com.karaoke.management.reponsitory.RoomTypeRepository;
import com.karaoke.management.reponsitory.UserAccountRepository;

@Service
public class ImportDatabaseService {
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	RoomTypeRepository roomTypeRepository;
	
	@Autowired
	FoodRepository foodRepository;
	
	@Autowired
    PasswordEncoder passwordEncoder;

	public ResponseEntity<?> improtDataBase() {
		try {
			importAccount();
			importRoomType();
			improtFood();
			importRoom();
			return new ResponseEntity<Object>(new ApiResponse(true, "Improt database successfully"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	private void importRoomType() {
		List<RoomType> roomTypes = new ArrayList<RoomType>();
		RoomType roomType1 = new RoomType("Phòng thường", 200000);
		roomTypes.add(roomType1);
		
		RoomType roomType2 = new RoomType("Phòng vip", 300000);
		roomTypes.add(roomType2);
		
		for (RoomType roomType : roomTypes) {
			if(!roomTypeRepository.existsByTypeName(roomType.getTypeName())) {
				roomTypeRepository.save(roomType);
			}
		}
	}

	private void importAccount() {
		UserAccount userAccount = new UserAccount("khongaica1", passwordEncoder.encode("123"), "Không Ai Cả");
		if(!userAccountRepository.existsByUserName(userAccount.getUserName())) {
			userAccountRepository.save(userAccount);
		}
	}

	private void improtFood() {
		List<Food> foods = new ArrayList<Food>();
		Food food1 = new Food("Nước suối", "Chai", 15000);
		foods.add(food1);
		
		Food food2 = new Food("Bia Tiger", "Lon", 25000);
		foods.add(food2);
		
		Food food3 = new Food("Trái cây", "Dĩa", 150000);
		foods.add(food3);
		
		Food food4 = new Food("Bánh snack", "Bich", 15000);
		foods.add(food4);
		
		Food food5 = new Food("Nước ngọt", "Chai", 20000);
		foods.add(food5);
		
		for (Food food : foods) {
			if(!foodRepository.existsByEatingName(food.getEatingName())) {
				foodRepository.save(food);
			}
		}
	}

	private void importRoom() {
		RoomType std = roomTypeRepository.findByTypeId(1);
		RoomType vip = roomTypeRepository.findByTypeId(2);
		
		List<Room> rooms = new ArrayList<Room>();
		Room room1 = new Room("STD 001", std, 0);
		rooms.add(room1);
		
		Room room2 = new Room("STD 002", std, 0);
		rooms.add(room2);
		
		Room room3 = new Room("STD 003", std, 0);
		rooms.add(room3);
		
		Room room4 = new Room("STD 004", std, 0);
		rooms.add(room4);

		Room room5 = new Room("VIP 001", vip, 0);
		rooms.add(room5);

		Room room6 = new Room("VIP 002", vip, 0);
		rooms.add(room6);
		
		Room room7 = new Room("VIP 003", vip, 0);
		rooms.add(room7);
		
		Room room8 = new Room("VIP 004", vip, 0);
		rooms.add(room8);
		
		for (Room room : rooms) {
			if(!roomRepository.existsByRoomName(room.getRoomName())) {
				roomRepository.save(room);
			}
		}
	}

}
