package com.karaoke.management.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.karaoke.management.api.request.BillDetailRequest;
import com.karaoke.management.api.response.ApiResponse;
import com.karaoke.management.api.response.BillDetailResponse;
import com.karaoke.management.api.response.BillResponse;
import com.karaoke.management.api.response.FoodResponse;
import com.karaoke.management.api.response.MessageResponse;
import com.karaoke.management.api.response.OrderResponse;
import com.karaoke.management.api.response.RoomResponse;
import com.karaoke.management.api.response.RoomTypeResponse;
import com.karaoke.management.entity.Bill;
import com.karaoke.management.entity.BillDetails;
import com.karaoke.management.entity.BillDetailsKey;
import com.karaoke.management.entity.Food;
import com.karaoke.management.entity.Room;
import com.karaoke.management.entity.UserAccount;
import com.karaoke.management.reponsitory.BillDetailsRepository;
import com.karaoke.management.reponsitory.BillRepository;
import com.karaoke.management.reponsitory.FoodRepository;
import com.karaoke.management.reponsitory.RoomRepository;
import com.karaoke.management.reponsitory.UserAccountRepository;

@Service
public class OrderService {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	BillRepository billRepository;

	@Autowired
	FoodRepository foodRepository;

	@Autowired
	BillDetailsRepository billDetailsRepository;

	@Autowired
	UserAccountRepository userAccountRepository;

	public ResponseEntity<?> checkinRoom(int roomId, Authentication authentication) {
		try {
			boolean checkStatus = roomRepository.existsByRoomIdAndStatus(roomId, 0);
			MessageResponse messageResponse = null;
			if (checkStatus) {
				OrderResponse orderResponse = createOrderWhenCheckIn(roomId, authentication.getName());
				if (orderResponse != null) {
					messageResponse = new MessageResponse("Checkin successfully", 200);
					return ResponseEntity.status(HttpStatus.OK).body(messageResponse);

				} else {
					messageResponse = new MessageResponse("Couldn't checkin", 404);
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
				}
			} else {
				messageResponse = new MessageResponse("Room not exist or room checked in", 404);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> findBillByRoom(int id) {
		try {
			if (roomRepository.existsByRoomIdAndStatus(id, 1)) {
				Room room = roomRepository.findByRoomId(id);
				Bill bill = billRepository.findFirstByRoomOrderByBillIdDesc(room);
				if (bill != null) {
					List<BillDetails> listBillDetails = billDetailsRepository.findByBill(bill);
					OrderResponse orderResponse = new OrderResponse();
					orderResponse = createOrderReponse(room, bill, listBillDetails);
					return ResponseEntity.ok(orderResponse);
				} else {
					room.setStatus(0);
					roomRepository.save(room);
					return new ResponseEntity<Object>(new ApiResponse(false, "Room uncheckin!"),
							HttpStatus.BAD_REQUEST);
				}
			}
			return new ResponseEntity<Object>(new ApiResponse(false, "Room doesn't exist or room uncheckin!"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> addBillDetailRequest(int id, @Valid BillDetailRequest billDetailRequest) {
		try {
			if (roomRepository.existsByRoomIdAndStatus(id, 1)) {
				Room room = roomRepository.findByRoomId(id);
				addBillDetailToOrderReponse(room, billDetailRequest);
				return findBillByRoom(id);
			}
			return new ResponseEntity<Object>(new ApiResponse(false, "Room doesn't exist or room uncheckin!"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> addListBillDetailRequest(int id, @Valid List<BillDetailRequest> listBillDetailRequest) {
		try {
			if (roomRepository.existsByRoomIdAndStatus(id, 1)) {
				Room room = roomRepository.findByRoomId(id);
				for (BillDetailRequest billDetailRequest : listBillDetailRequest) {
					addBillDetailToOrderReponse(room, billDetailRequest);
				}
				return findBillByRoom(id);
			}
			return new ResponseEntity<Object>(new ApiResponse(false, "Room doesn't exist or room uncheckin!"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<?> deleteBillDetail(int id, @Valid BillDetailRequest billDetailRequest) {
		try {
			if (roomRepository.existsByRoomIdAndStatus(id, 1)) {
				boolean checkDeleteBillDetail = deleteBillDetail(billDetailRequest);
				if (checkDeleteBillDetail) {
					return findBillByRoom(id);
				}
				return new ResponseEntity<Object>(new ApiResponse(false, "Menu or bill doesn't exist!"),
						HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<Object>(new ApiResponse(false, "Room doesn't exist or room uncheckin!"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> checkoutRoom(int roomId, Authentication authentication) {
		try {
			boolean checkStatus = roomRepository.existsByRoomIdAndStatus(roomId, 1);
			MessageResponse messageResponse = null;
			if (checkStatus) {
				OrderResponse orderResponse = createOrderWhenCheckOut(roomId, authentication.getName());
				if (orderResponse != null) {
					return ResponseEntity.ok(orderResponse);
				} else {
					messageResponse = new MessageResponse("Couldn't checkout", 404);
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
				}
			} else {
				messageResponse = new MessageResponse("Room not exist or room can't checkout in", 404);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	private boolean deleteBillDetail(@Valid BillDetailRequest billDetailRequest) {
		Bill bill = billRepository.findByBillId(billDetailRequest.getBillId());
		Food food = foodRepository.findByFoodId(billDetailRequest.getFoodId());
		if (checkBillAndFoodExist(bill, food)) {
			try {
				BillDetails billDetails = billDetailsRepository.findByBillAndFood(bill, food);
				billDetailsRepository.delete(billDetails);
				updateTotalPrice(bill);
			} catch (Exception e) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	private boolean checkBillAndFoodExist(Bill bill, Food food) {

		if (bill == null || food == null) {
			return false;
		}
		return true;

	}

	private OrderResponse createOrderWhenCheckIn(int roomId, String username) {
		OrderResponse orderResponse = new OrderResponse();
		Room room = updateCheckin(roomId);
		RoomResponse roomResponse = setRoomResponse(room);
		orderResponse.setRoom(roomResponse);

		Bill bill = creatBill(roomId, username);
		BillResponse billResponse = new BillResponse(bill.getBillId(), bill.getRoom().getRoomId(), bill.getCheckin(),
				bill.getUserAccount().getId());
		orderResponse.setBill(billResponse);

		return orderResponse;
	}

	private OrderResponse createOrderWhenCheckOut(int roomId, String username) {
		OrderResponse orderResponse = new OrderResponse();
		Room room = updateCheckout(roomId);

		Bill bill = updateBill(roomId, username);
		bill = calculateTotal(bill);
		List<BillDetails> listBillDetails = billDetailsRepository.findByBill(bill);
		if(bill.getTotal() < 100000.00) {
			bill.setTotal(100000.00);
			billRepository.save(bill);
		}
		orderResponse = createOrderReponse(room, bill, listBillDetails);

		return orderResponse;
	}

	private Bill creatBill(@Valid int roomId, String username) {
		LocalDateTime checkin = LocalDateTime.now();
		Room room = roomRepository.findByRoomId(roomId);
		UserAccount userAccount = userAccountRepository.findByUserName(username);
		Bill bill = new Bill(room, checkin, userAccount);
		Bill result = billRepository.save(bill);
		return result;
	}

	private Room updateCheckin(int roomId) {
		Room room = roomRepository.findByRoomId(roomId);

		room.setStatus(1);

		Room result = roomRepository.save(room);

		return result;
	}

	private Room updateCheckout(int roomId) {
		Room room = roomRepository.findByRoomId(roomId);

		room.setStatus(0);

		room = roomRepository.save(room);

		return room;
	}

	private RoomResponse setRoomResponse(Room room) {
		RoomTypeResponse roomTypeResponse = new RoomTypeResponse(room.getRoomType().getTypeId(),
				room.getRoomType().getTypeName(), room.getRoomType().getPrice());

		RoomResponse roomResponse = new RoomResponse(room.getRoomId(), room.getRoomName(), roomTypeResponse,
				room.getStatus());
		return roomResponse;
	}

	private Bill updateBill(@Valid int roomId, String username) {
		LocalDateTime checkout = LocalDateTime.now();
		Room room = roomRepository.findByRoomId(roomId);
		UserAccount userAccount = userAccountRepository.findByUserName(username);
		Bill bill = billRepository.findFirstByRoomOrderByBillIdDesc(room);
		bill.setUserAccoutId(userAccount);
		bill.setCheckout(checkout);

		bill = billRepository.save(bill);

		return bill;
	}

	private Bill calculateTotal(Bill bill) {
		double total = 0;
		total = calculateTimeMoney(bill.getCheckin(), bill.getCheckout(),
				(double) bill.getRoom().getRoomType().getPrice());

		bill = updateTotalPrice(bill);

		bill.setTotal(total + bill.getTotal());

		bill = billRepository.save(bill);
		return bill;
	}

	private double calculateTimeMoney(LocalDateTime checkinTime, LocalDateTime checkoutTime, double priceRoom) {

		double result = 0;

		LocalDateTime tempDateTime = LocalDateTime.from(checkinTime);

		long hours = tempDateTime.until(checkoutTime, ChronoUnit.HOURS);
		tempDateTime = tempDateTime.plusHours(hours);

		result += hours;

		long minutes = tempDateTime.until(checkoutTime, ChronoUnit.MINUTES);
		tempDateTime = tempDateTime.plusMinutes(minutes);

		result += (double) minutes / 60;

		long seconds = tempDateTime.until(checkoutTime, ChronoUnit.SECONDS);

		result += (double) seconds / 3600;

		result = Math.round(result * 100.0) / 100.0;

		result = (double) result * priceRoom;

		return result;
	}

	private OrderResponse createOrderReponse(Room room, Bill bill, List<BillDetails> listBillDetails) {
		OrderResponse orderResponse = new OrderResponse();
		RoomResponse roomResponse = setRoomResponse(room);
		orderResponse.setRoom(roomResponse);

		BillResponse billResponse = new BillResponse(bill.getBillId(), bill.getRoom().getRoomId(), bill.getCheckin(),
				bill.getCheckout(), bill.getTotal(), bill.getUserAccount().getId());
		orderResponse.setBill(billResponse);

		List<BillDetailResponse> listBillDetailResponse = new ArrayList<BillDetailResponse>();

		for (BillDetails billDetails : listBillDetails) {
			Food food = billDetails.getFood();
			FoodResponse foodResponse = new FoodResponse(food.getFoodId(), food.getEatingName(), food.getUnit(),
					food.getPrice());

			BillDetailResponse billDetailResponse = new BillDetailResponse();
			if (billDetails != null) {
				billDetailResponse.setFoodResponse(foodResponse);
				billDetailResponse.setNumber(billDetails.getNumber());
				billDetailResponse.setUnitPrice(billDetails.getUnitPrice());
				listBillDetailResponse.add(billDetailResponse);
			}
		}
		orderResponse.setListBillDetailResponse(listBillDetailResponse);

		return orderResponse;
	}

	private Bill updateTotalPrice(Bill bill) {
		List<BillDetails> listBillDetails = billDetailsRepository.findByBill(bill);
		double totalPrice = 0;

		for (BillDetails billDetails : listBillDetails) {
			totalPrice += billDetails.getUnitPrice();
		}

		bill.setTotal(totalPrice);
		bill = billRepository.save(bill);
		return bill;
	}

	private void addBillDetailToOrderReponse(Room room, @Valid BillDetailRequest billDetailRequest) {
		Bill bill = billRepository.findByBillId(billDetailRequest.getBillId());
		Food food = foodRepository.findByFoodId(billDetailRequest.getFoodId());
		if (bill != null) {
			if (food != null) {
				boolean checkAddBillDetail = addBillDetail(billDetailRequest);

				if (checkAddBillDetail) {
					bill = updateTotalPrice(bill);
					bill = billRepository.findByBillId(billDetailRequest.getBillId());
				}
			}
		} else {
			room.setStatus(0);
			roomRepository.save(room);
		}
	}

	private boolean addBillDetail(@Valid BillDetailRequest billDetailRequest) {
		try {
			Optional<Food> food = foodRepository.findById(billDetailRequest.getFoodId());
			Bill bill = billRepository.findByBillId(billDetailRequest.getBillId());
			BillDetails billDetails = billDetailsRepository.findByBillAndFood(bill, food.get());
			int number = billDetailRequest.getNumber();
			if (billDetails == null) {
				BillDetailsKey billDetailsKey = new BillDetailsKey(billDetailRequest.getBillId(),
						billDetailRequest.getFoodId());
				billDetails = new BillDetails(billDetailsKey);
				if (number <= 0) {
					return false;
				}
			} else {
				number += billDetails.getNumber();
				if (number <= 0) {
					billDetailsRepository.delete(billDetails);
					return true;
				}
			}

			double price = food.get().getPrice() * number;
			billDetails.setNumber(number);
			billDetails.setUnitPrice(price);
			billDetails.setBill(bill);
			billDetails.setFood(food.get());
			billDetailsRepository.save(billDetails);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
