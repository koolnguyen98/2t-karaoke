package com.karaoke.management.api.controller;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
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
	public ResponseEntity<Object> create(@Valid @RequestBody RoomRequest roomRequest, HttpServletRequest request) throws URISyntaxException {
		return roomService.createRoom(roomRequest, request);
	}

	@GetMapping(value = Urls.API_ROOM)
	public ResponseEntity<?> findAll(HttpServletRequest request) {
		return roomService.findAll(request);
	}

	@GetMapping(value = Urls.API_ROOM_FIND_BY_ID)
	public ResponseEntity<?> findRoomById(@PathVariable int id, HttpServletRequest request) {
		return roomService.findById(id, request);
	}

	@PutMapping(value = Urls.API_ROOM_UPDATE_BY_ID)
	public ResponseEntity<?> updateRoom(@RequestBody RoomRequest roomRequest, @PathVariable int id, HttpServletRequest request) {
		return roomService.updateRoomById(id, roomRequest, request);
	}

	@DeleteMapping(value = Urls.API_ROOM_DELETE_BY_ID)
	public ResponseEntity<?> deleteRoom(@PathVariable int id, HttpServletRequest request) {

		return roomService.deleteRoomById(id, request);

	}
	
	@PostMapping(value = Urls.API_ROOM_TYPE_CREATE)
	public ResponseEntity<Object> createRoomType(@Valid @RequestBody RoomTypeRequest roomTypeRequest, HttpServletRequest request) throws URISyntaxException {
		return roomTypeService.createRoomType(roomTypeRequest, request);
	}

	@GetMapping(value = Urls.API_ROOM_TYPE)
	public ResponseEntity<?> findAllRoomType(HttpServletRequest request) {
		return roomTypeService.findAll(request);
	}

	@GetMapping(value = Urls.API_ROOM_TYPE_FIND_BY_ID)
	public ResponseEntity<?> findRoomTypeById(@PathVariable int id, HttpServletRequest request) {
		return roomTypeService.findById(id, request);
	}

	@PutMapping(value = Urls.API_ROOM_TYPE_UPDATE_BY_ID)
	public ResponseEntity<?> updateRoomType(@RequestBody RoomTypeRequest roomTypeRequest, @PathVariable int id, HttpServletRequest request) {
		return roomTypeService.updateById(id, roomTypeRequest, request);
	}

	@DeleteMapping(value = Urls.API_ROOM_TYPE_DELETE_BY_ID)
	public ResponseEntity<?> deleteRoomType(@PathVariable int id, HttpServletRequest request) {

		return roomTypeService.deleteById(id, request);

	}

}
