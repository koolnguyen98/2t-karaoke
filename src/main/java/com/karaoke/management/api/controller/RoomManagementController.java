package com.karaoke.management.api.controller;

import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.karaoke.management.api.Urls;
import com.karaoke.management.api.request.RoomRequest;
import com.karaoke.management.api.request.RoomTypeRequest;
import com.karaoke.management.service.RoomService;
import com.karaoke.management.service.RoomTypeService;

@RestController
public class RoomManagementController {

	@Autowired
	RoomService roomService;
	
	@Autowired
	RoomTypeService roomTypeService;

	@PostMapping(value = Urls.API_ROOM_CREATE)
	public ResponseEntity<Object> create(@Valid @RequestBody RoomRequest roomRequest) throws URISyntaxException {
		return roomService.createRoom(roomRequest);
	}

	@GetMapping(value = Urls.API_ROOM)
	public ResponseEntity<?> findAll() {
		return roomService.findAll();
	}

	@GetMapping(value = Urls.API_ROOM_FIND_BY_ID)
	public ResponseEntity<?> findRoomById(@PathVariable int id) {
		return roomService.findById(id);
	}

	@PutMapping(value = Urls.API_ROOM_UPDATE_BY_ID)
	public ResponseEntity<?> updateRoom(@RequestBody RoomRequest roomRequest, @PathVariable int id) {
		return roomService.updateRoomById(id, roomRequest);
	}

	@DeleteMapping(value = Urls.API_ROOM_DELETE_BY_ID)
	public ResponseEntity<?> deleteRoom(@PathVariable int id) {

		return roomService.deleteRoomById(id);

	}
	
	@PostMapping(value = Urls.API_ROOM_TYPE_CREATE)
	public ResponseEntity<Object> createRoomType(@Valid @RequestBody RoomTypeRequest roomTypeRequest) throws URISyntaxException {
		return roomTypeService.createRoomType(roomTypeRequest);
	}

	@GetMapping(value = Urls.API_ROOM_TYPE)
	public ResponseEntity<?> findAllRoomType() {
		return roomTypeService.findAll();
	}

	@GetMapping(value = Urls.API_ROOM_TYPE_FIND_BY_ID)
	public ResponseEntity<?> findRoomTypeById(@PathVariable int id) {
		return roomTypeService.findById(id);
	}

	@PutMapping(value = Urls.API_ROOM_TYPE_UPDATE_BY_ID)
	public ResponseEntity<?> updateRoomType(@RequestBody RoomTypeRequest roomTypeRequest, @PathVariable int id) {
		return roomTypeService.updateById(id, roomTypeRequest);
	}

	@DeleteMapping(value = Urls.API_ROOM_TYPE_DELETE_BY_ID)
	public ResponseEntity<?> deleteRoomType(@PathVariable int id) {

		return roomTypeService.deleteById(id);

	}

}
