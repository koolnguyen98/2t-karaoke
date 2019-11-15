package com.karaoke.management.service;

import java.time.LocalDateTime;
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
import com.karaoke.management.api.response.MenuResponse;
import com.karaoke.management.api.response.MessageResponse;
import com.karaoke.management.api.response.OrderResponse;
import com.karaoke.management.api.response.RoomResponse;
import com.karaoke.management.api.response.RoomTypeResponse;
import com.karaoke.management.entity.Bill;
import com.karaoke.management.entity.BillDetails;
import com.karaoke.management.entity.BillDetailsKey;
import com.karaoke.management.entity.Menu;
import com.karaoke.management.entity.Room;
import com.karaoke.management.entity.UserAccount;
import com.karaoke.management.reponsitory.BillDetailsRepository;
import com.karaoke.management.reponsitory.BillRepository;
import com.karaoke.management.reponsitory.MenuRepository;
import com.karaoke.management.reponsitory.RoomRepository;
import com.karaoke.management.reponsitory.UserAccountRepository;

@Service
public class OrderService {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	BillRepository billRepository;

	@Autowired
	MenuRepository menuRepository;

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
		Menu menu = menuRepository.findByMenuId(billDetailRequest.getMenuId());
		if (checkBillAndMenuExist(bill, menu)) {
			try {
				BillDetails billDetails = billDetailsRepository.findByBillAndMenu(bill, menu);
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

	private boolean checkBillAndMenuExist(Bill bill, Menu menu) {

		if (bill == null || menu == null) {
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
		RoomResponse roomResponse = setRoomResponse(room);
		orderResponse.setRoom(roomResponse);

		Bill bill = updateBill(roomId, username);
		BillResponse billResponse = new BillResponse(bill.getBillId(), bill.getRoom().getRoomId(), bill.getCheckin(),
				bill.getChecout(), bill.getTotal(), bill.getUserAccount().getId());
		orderResponse.setBill(billResponse);

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

		double total = calculateTotal(bill);
		bill.setTotal(total);

		return bill;
	}

	private double calculateTotal(Bill bill) {
		double total = 0;
		total = calculateTimeMoney(bill.getCheckin(), bill.getChecout());

		List<BillDetails> billDetails = billDetailsRepository.findByBill(bill);
		for (BillDetails billDetail : billDetails) {
			total += (billDetail.getUnitPrice() * (double) billDetail.getNumber());
		}
		return total;
	}

	private double calculateTimeMoney(LocalDateTime checkin, LocalDateTime checout) {
		// System.out.println(checkin. (checout));
		return 0;
	}

	private OrderResponse createOrderReponse(Room room, Bill bill, List<BillDetails> listBillDetails) {
		OrderResponse orderResponse = new OrderResponse();
		RoomResponse roomResponse = setRoomResponse(room);
		orderResponse.setRoom(roomResponse);

		BillResponse billResponse = new BillResponse(bill.getBillId(), bill.getRoom().getRoomId(), bill.getCheckin(),
				bill.getTotal(), bill.getUserAccount().getId());
		orderResponse.setBill(billResponse);

		List<BillDetailResponse> listBillDetailResponse = new ArrayList<BillDetailResponse>();

		for (BillDetails billDetails : listBillDetails) {
			Menu menu = billDetails.getMenu();
			MenuResponse menuResponse = new MenuResponse(menu.getMenuId(), menu.getEatingName(), menu.getUnit(),
					menu.getPrice());

			BillDetailResponse billDetailResponse = new BillDetailResponse();
			if (billDetails != null) {
				billDetailResponse.setMenuResponse(menuResponse);
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
		Menu menu = menuRepository.findByMenuId(billDetailRequest.getMenuId());
		if (bill != null) {
			if (menu != null) {
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
			Optional<Menu> menu = menuRepository.findById(billDetailRequest.getMenuId());
			Bill bill = billRepository.findByBillId(billDetailRequest.getBillId());
			BillDetails billDetails = billDetailsRepository.findByBillAndMenu(bill, menu.get());
			int number = billDetailRequest.getNumber();
			if (billDetails == null) {
				BillDetailsKey billDetailsKey = new BillDetailsKey(billDetailRequest.getBillId(),
						billDetailRequest.getMenuId());
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

			double price = menu.get().getPrice() * number;
			billDetails.setNumber(number);
			billDetails.setUnitPrice(price);
			billDetails.setBill(bill);
			billDetails.setMenu(menu.get());
			billDetailsRepository.save(billDetails);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
