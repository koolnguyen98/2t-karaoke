package com.karaoke.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.karaoke.management.api.WriterLog;
import com.karaoke.management.api.request.RoomTypeRequest;
import com.karaoke.management.api.response.ApiResponse;
import com.karaoke.management.api.response.MessageResponse;
import com.karaoke.management.api.response.RoomTypeResponse;
import com.karaoke.management.entity.RoomType;
import com.karaoke.management.reponsitory.RoomRepository;
import com.karaoke.management.reponsitory.RoomTypeRepository;

@Service
public class RoomTypeService {

	@Autowired
	RoomTypeRepository roomTypeRepository;

	@Autowired
	RoomRepository roomRepository;
	
	Logger logger = WriterLog.getLogger("Room Type Service");

	public ResponseEntity<Object> createRoomType(RoomTypeRequest roomTypeRequest) {
		try {
			if(roomTypeRepository.existsByTypeName(roomTypeRequest.getTypeName())) {
				logger.info("Create room type name is already taken!");
	            return new ResponseEntity<Object>(new ApiResponse(false, "Room type name is already taken!"),
	                    HttpStatus.BAD_REQUEST);
	        }
	    	
	    	if(roomTypeRequest.getPrice() < 0 || roomTypeRequest.getPrice() == 0) {
	    		logger.info("Create room Type price > 0!");
	            return new ResponseEntity<Object>(new ApiResponse(false, "Price > 0!"),
	                    HttpStatus.BAD_REQUEST);
	        }
	    	
	        RoomType roomType = createRoomTypePrivate(roomTypeRequest);
	        if (roomType == null) {
	        	logger.info("Cann't creat room type!");
	        	return new ResponseEntity<Object>(new ApiResponse(false, "Cann't creat room type!"), HttpStatus.NOT_FOUND);
	        } else {
	        	logger.info("Create room type successfully!");
	        	return new ResponseEntity<Object>(new ApiResponse(false, "Create room type successfully!"),
	                    HttpStatus.OK);
	        }
		} catch (Exception e) {
			logger.warning(e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	private RoomType createRoomTypePrivate(RoomTypeRequest roomTypeRequest) {
		RoomType roomType = roomTypeRepository.findByTypeName(roomTypeRequest.getTypeName());
		if (roomType == null) {

			RoomType createdRoomType = new RoomType(roomTypeRequest.getTypeName(), roomTypeRequest.getPrice());
			RoomType result = roomTypeRepository.save(createdRoomType);

			return result;
		}
		return null;
	}

	public ResponseEntity<?> findAll() {
		try {
			List<RoomType> roomTypes = roomTypeRepository.findAll();
			List<RoomTypeResponse> roomTypeResponses = new ArrayList<RoomTypeResponse>();
			for (RoomType roomType : roomTypes) {
				RoomTypeResponse roomTypeResponse = new RoomTypeResponse(roomType.getTypeId(), roomType.getTypeName(),
						roomType.getPrice());
				roomTypeResponses.add(roomTypeResponse);
			}
			logger.info("Find all room type successfully!");
			return ResponseEntity.ok(roomTypeResponses);

		} catch (Exception e) {
			logger.warning(e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> findById(int id) {
		try {
			Optional<RoomType> roomType = roomTypeRepository.findById(id);
			if (!roomType.isPresent()) {
				logger.info("Room type doesn't exist!");
				return new ResponseEntity<Object>(new ApiResponse(false, "Room type doesn't exist!"),
						HttpStatus.NOT_FOUND);
			} else {
				RoomTypeResponse roomTypeResponse = new RoomTypeResponse(roomType.get().getTypeId(),
						roomType.get().getTypeName(), roomType.get().getPrice());
				logger.info("Find room type " + id + " successfully!");
				return ResponseEntity.ok(roomTypeResponse);
			}
		} catch (Exception e) {
			logger.warning(e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> updateById(int id, RoomTypeRequest roomTypeRequest) {
		try {
			RoomType roomType = roomTypeRepository.findByTypeId(id);
			if (roomType == null) {
				logger.info("Room type doesn't exist!");
				return new ResponseEntity<Object>(new ApiResponse(false, "Room type doesn't exist!"),
						HttpStatus.NOT_FOUND);
			} else {
				if (roomTypeRepository.existsByTypeName(roomTypeRequest.getTypeName())) {
					logger.info("Create room type name is already taken!");
					return new ResponseEntity<Object>(new ApiResponse(false, "Room type name is already taken!"),
							HttpStatus.BAD_REQUEST);
				}

				if (roomTypeRequest.getPrice() < 0 || roomTypeRequest.getPrice() == 0) {
					logger.info("Update room type price > 0");
					return new ResponseEntity<Object>(new ApiResponse(false, "Price > 0!"), HttpStatus.BAD_REQUEST);
				}
				RoomType updatedRoomType = updateRoomTypeById(roomType, roomTypeRequest);
				RoomTypeResponse roomTypeResponse = new RoomTypeResponse(updatedRoomType.getTypeId(),
						updatedRoomType.getTypeName(), updatedRoomType.getPrice());
				logger.info("Update room type " + id + " successfully!");
				return ResponseEntity.ok(roomTypeResponse);
			}
		} catch (Exception e) {
			logger.warning(e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	private RoomType updateRoomTypeById(RoomType roomType, RoomTypeRequest roomTypeRequest) {
		if (roomTypeRequest.getTypeName() != null && roomTypeRequest.getTypeName() != "") {
			roomType.setTypeName(roomTypeRequest.getTypeName());
		}
		if (roomTypeRequest.getPrice() != -1) {
			roomType.setPrice(roomTypeRequest.getPrice());
		}
		RoomType result = roomTypeRepository.save(roomType);

		return result;
	}

	public ResponseEntity<?> deleteById(int id) {
		try {
			boolean deleteRoomType = deleteRoomTypeById(id);
			MessageResponse messageResponse = null;
			if (!deleteRoomType) {
				logger.info("Update room type " + id + " unsuccessfully!");
				messageResponse = new MessageResponse("Room type doesn't exist or used!", 404);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
			}
			logger.info("Update room type " + id + " successfully!");
			messageResponse = new MessageResponse("Delete room type access", 200);
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (Exception e) {
			logger.warning(e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	private boolean deleteRoomTypeById(int id) {
		RoomType roomType = roomTypeRepository.findByTypeId(id);

		if (roomType != null) {
			boolean checkExitRoom = roomRepository.existsByRoomType(roomType);
			System.out.println("Check: " + checkExitRoom);
			if (!checkExitRoom) {
				roomTypeRepository.delete(roomType);
				return !checkExitRoom;
			}

			return !checkExitRoom;

		}

		return false;
	}

}
