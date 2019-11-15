package com.karaoke.management.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.karaoke.management.api.request.RoomRequest;
import com.karaoke.management.api.response.ApiResponse;
import com.karaoke.management.api.response.Response;
import com.karaoke.management.api.response.RoomResponse;
import com.karaoke.management.api.response.RoomTypeResponse;
import com.karaoke.management.entity.Room;
import com.karaoke.management.entity.RoomType;
import com.karaoke.management.reponsitory.BillRepository;
import com.karaoke.management.reponsitory.RoomRepository;
import com.karaoke.management.reponsitory.RoomTypeRepository;

@Service
public class RoomService {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	RoomTypeRepository roomTypeRepository;

	@Autowired
	BillRepository billRepository;

	public ResponseEntity<Object> updateRoomById(int id, RoomRequest roomRequest) {
		try {
			boolean checkRoom = roomRepository.existsById(id);

			if (checkRoom) {
				Room roomUpdate = roomRepository.findByRoomId(id);

				roomUpdate = updateRoom(roomUpdate, roomRequest);
				Response roomResponse = new RoomResponse(roomUpdate.getRoomId(), roomUpdate.getRoomName(),
						new RoomTypeResponse(roomUpdate.getRoomType().getTypeId(),
								roomUpdate.getRoomType().getTypeName(), roomUpdate.getRoomType().getPrice()),
						roomUpdate.getStatus());
				return new ResponseEntity<Object>(new ApiResponse(true, roomResponse), HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new ApiResponse(false, "Could't update room!"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<Object> deleteRoomById(int id) {
		try {
			Room room = roomRepository.findByRoomId(id);
			if (room != null) {
				boolean checkExitBill = billRepository.existsByRoom(room);

				if (!checkExitBill) {
					roomRepository.delete(room);
					return new ResponseEntity<Object>(new ApiResponse(false, "Delete Room Access"), HttpStatus.OK);
				}
				return new ResponseEntity<Object>(new ApiResponse(false, "Could't delete room!"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Object>(new ApiResponse(false, "Room does not exist"), HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<Object> createRoom(RoomRequest roomRequest) {
		try {
			if (checkIsRoomExist(roomRequest.getRoomName().trim())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Room name is already taken!"),
						HttpStatus.BAD_REQUEST);
			}

			if (checkRoomRequest(roomRequest.getRoomTypeId())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Room Type no exist!"),
						HttpStatus.BAD_REQUEST);
			}

			Room room = saveRoom(roomRequest);
			if (room == null) {
				return ResponseEntity.notFound().build();
			} else {
				URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
						.buildAndExpand(room.getRoomId()).toUri();
				RoomResponse roomResponse = createRoomResponse(room);
				return ResponseEntity.created(uri).body(roomResponse);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> findAll() {
		try {
			List<Room> listRoom = roomRepository.findAll();
			if (listRoom != null) {
				List<Response> roomResponses = new ArrayList<Response>();
				for (Room room : listRoom) {
					RoomTypeResponse roomTypeResponse = new RoomTypeResponse(room.getRoomType().getTypeId(),
							room.getRoomType().getTypeName(), room.getRoomType().getPrice());
					Response roomReponse = new RoomResponse(room.getRoomId(), room.getRoomName(), roomTypeResponse,
							room.getStatus());
					roomResponses.add(roomReponse);
				}
				return ResponseEntity.ok(roomResponses);
			} else {
				return new ResponseEntity<Object>(new ApiResponse(false, "Room does't exist!"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> findById(int id) {
		try {
			Room room = roomRepository.findByRoomId(id);
			if (room == null) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Could't get room!"), HttpStatus.NOT_FOUND);
			} else {
				RoomTypeResponse roomTypeResponse = new RoomTypeResponse(room.getRoomType().getTypeId(),
						room.getRoomType().getTypeName(), room.getRoomType().getPrice());
				RoomResponse roomResponse = new RoomResponse(room.getRoomId(), room.getRoomName(), roomTypeResponse,
						room.getStatus());

				return ResponseEntity.ok(roomResponse);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	private RoomResponse createRoomResponse(Room room) {
		RoomResponse roomResponse = new RoomResponse(room.getRoomId(), room.getRoomName(),
				new RoomTypeResponse(room.getRoomType().getTypeId(), room.getRoomType().getTypeName(),
						room.getRoomType().getPrice()),
				room.getStatus());
		return roomResponse;
	}

	private Room saveRoom(RoomRequest roomRequest) {

		RoomType roomType = roomTypeRepository.findByTypeId(roomRequest.getRoomTypeId());
		if (roomType != null) {
			Room room = new Room(roomRequest.getRoomName(), roomType, roomRequest.getStatus());
			Room result = roomRepository.save(room);
			return result;
		}
		return null;

	}

	private boolean checkIsRoomExist(String roomName) {
		boolean checkIsRoomExist = roomRepository.existsByRoomName(roomName) || roomName == "" || roomName == null;
		return checkIsRoomExist;
	}

	private boolean checkRoomRequest(int roomId) {
		boolean result = roomTypeRepository.existsById(roomId);
		return result;
	}

	private Room updateRoomName(Room room, String roomName) {

		if (roomName != null && roomName != "") {
			room.setRoomName(roomName);
		}

		return room;
	}

	private Room updateRoom(Room room, RoomRequest roomRequest) {
		room = updateRoomName(room, roomRequest.getRoomName().trim());
		room = updateRoomType(room, roomRequest.getRoomTypeId());
		room = updateRoomStatus(room, roomRequest.getStatus());
		Room result = roomRepository.save(room);

		return result;
	}

	private Room updateRoomType(Room room, int idRoomType) {

		if (roomTypeRepository.existsById(idRoomType)) {
			room.setRoomType(roomTypeRepository.findByTypeId(idRoomType));
		}

		return room;
	}

	private Room updateRoomStatus(Room room, int roomStatus) {

		if (roomStatus == 1 || roomStatus == 0) {
			room.setStatus(roomStatus);
		}

		return room;
	}

}
