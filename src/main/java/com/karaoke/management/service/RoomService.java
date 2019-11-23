package com.karaoke.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.karaoke.management.api.WriterLog;
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

	Logger logger = WriterLog.getLogger(RoomService.class.toString());

	public ResponseEntity<Object> updateRoomById(int id, RoomRequest roomRequest, HttpServletRequest request) {
		try {
			boolean checkRoom = roomRepository.existsById(id);

			if (checkRoom) {
				Room roomUpdate = roomRepository.findByRoomId(id);

				roomUpdate = updateRoom(roomUpdate, roomRequest);
				Response roomResponse = new RoomResponse(roomUpdate.getRoomId(), roomUpdate.getRoomName(),
						new RoomTypeResponse(roomUpdate.getRoomType().getTypeId(),
								roomUpdate.getRoomType().getTypeName(), roomUpdate.getRoomType().getPrice()),
						roomUpdate.getStatus());
				logger.info("Client " + request.getRemoteAddr() + ": " + "Update room " + id + " successfully");
				return new ResponseEntity<Object>(new ApiResponse(true, roomResponse), HttpStatus.OK);
			} else {
				logger.info("Client " + request.getRemoteAddr() + ": " + "Update room " + id + " unsuccessfully");
				return new ResponseEntity<Object>(new ApiResponse(false, "Client " + request.getRemoteAddr() + ": " + "Could't update room!"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.warning("Client " + request.getRemoteAddr() + ": " + e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<Object> deleteRoomById(int id, HttpServletRequest request) {
		try {
			Room room = roomRepository.findByRoomId(id);
			if (room != null) {
				boolean checkExitBill = billRepository.existsByRoom(room);

				if (!checkExitBill) {
					roomRepository.delete(room);
					logger.info("Client " + request.getRemoteAddr() + ": " + "Delete room " + id + " successfully");
					return new ResponseEntity<Object>(new ApiResponse(false, "Delete Room Access"), HttpStatus.OK);
				}
				logger.info("Client " + request.getRemoteAddr() + ": " + "Delete room " + id + " unsuccessfully");
				return new ResponseEntity<Object>(new ApiResponse(false, "Could't delete room!"), HttpStatus.NOT_FOUND);
			}
			logger.info("Client " + request.getRemoteAddr() + ": " + "Delete room " + id + " unsuccessfully");
			return new ResponseEntity<Object>(new ApiResponse(false, "Room does not exist"), HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			logger.warning("Client " + request.getRemoteAddr() + ": " + e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<Object> createRoom(RoomRequest roomRequest, HttpServletRequest request) {
		try {
			if (checkIsRoomExist(roomRequest.getRoomName().trim())) {
				logger.info("Client " + request.getRemoteAddr() + ": " + "Create room name is already taken!");
				return new ResponseEntity<Object>(new ApiResponse(false, "Room name is already taken!"),
						HttpStatus.BAD_REQUEST);
			}

			if (!checkRoomRequest(roomRequest.getRoomTypeId())) {
				logger.info("Client " + request.getRemoteAddr() + ": " + "Create room Type no exist!");
				return new ResponseEntity<Object>(new ApiResponse(false, "Room Type no exist!"),
						HttpStatus.BAD_REQUEST);
			}

			Room room = saveRoom(roomRequest);
			if (room == null) {
				logger.info("Client " + request.getRemoteAddr() + ": " + "Create room unsuccessfully!");
				return ResponseEntity.notFound().build();
			} else {
				logger.info("Client " + request.getRemoteAddr() + ": " + "Create room successfully!");
				return ResponseEntity.ok("Successfully");
			}
		} catch (Exception e) {
			logger.warning("Client " + request.getRemoteAddr() + ": " + e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> findAll(HttpServletRequest request) {
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
				logger.info("Client " + request.getRemoteAddr() + ": " + "Get all room successfully");
				return ResponseEntity.ok(roomResponses);
			} else {
				logger.info("Client " + request.getRemoteAddr() + ": " + "Room does't exist!");
				return new ResponseEntity<Object>(new ApiResponse(false, "Room does't exist!"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.warning("Client " + request.getRemoteAddr() + ": " + e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> findById(int id, HttpServletRequest request) {
		try {
			Room room = roomRepository.findByRoomId(id);
			if (room == null) {
				logger.info("Client " + request.getRemoteAddr() + ": " + "Get room by " + id + " unsuccessfully");
				return new ResponseEntity<Object>(new ApiResponse(false, "Could't get room!"), HttpStatus.NOT_FOUND);
			} else {
				RoomTypeResponse roomTypeResponse = new RoomTypeResponse(room.getRoomType().getTypeId(),
						room.getRoomType().getTypeName(), room.getRoomType().getPrice());
				RoomResponse roomResponse = new RoomResponse(room.getRoomId(), room.getRoomName(), roomTypeResponse,
						room.getStatus());
				logger.info("Client " + request.getRemoteAddr() + ": " + "Get room by " + id + " successfully");
				return ResponseEntity.ok(roomResponse);
			}
		} catch (Exception e) {
			logger.warning("Client " + request.getRemoteAddr() + ": " + e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

//	private RoomResponse createRoomResponse(Room room) {
//		RoomResponse roomResponse = new RoomResponse(room.getRoomId(), room.getRoomName(),
//				new RoomTypeResponse(room.getRoomType().getTypeId(), room.getRoomType().getTypeName(),
//						room.getRoomType().getPrice()),
//				room.getStatus());
//		return roomResponse;
//	}

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
