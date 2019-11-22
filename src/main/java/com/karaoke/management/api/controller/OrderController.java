package com.karaoke.management.api.controller;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.karaoke.management.api.Urls;
import com.karaoke.management.api.request.BillDetailRequest;
import com.karaoke.management.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping(value = Urls.API_ORDER_CHECKIN)
	public ResponseEntity<?> checkin(@PathVariable int roomId, Authentication authentication) throws URISyntaxException {
		return orderService.checkinRoom(roomId, authentication);
	}
	
	@GetMapping(value = Urls.API_ORDER_FIND_BILL)
	public ResponseEntity<?> findBillByRoom(@PathVariable int roomId) throws URISyntaxException {
		return orderService.findBillByRoom(roomId);
	}
	
	@PostMapping(value = Urls.API_ORDER_ADD_BILL_DETAILS)
	public ResponseEntity<?> addBillDetail(@PathVariable int roomId, @Valid @RequestBody BillDetailRequest billDetailRequest) throws URISyntaxException {
		return orderService.addBillDetailRequest(roomId, billDetailRequest);
	}
	
	@PostMapping(value = Urls.API_ORDER_ADD_LIST_BILL_DETAILS)
	public ResponseEntity<?> addListBillDetail(@PathVariable int roomId, @Valid @RequestBody List<BillDetailRequest> listBillDetailRequest) throws URISyntaxException {
		return orderService.addListBillDetailRequest(roomId, listBillDetailRequest);
	}
	
	@PostMapping(value = Urls.API_ORDER_DELETE_BILL_DETAILS)
	public ResponseEntity<?> deleteBillDetail(@PathVariable int roomId, @Valid @RequestBody BillDetailRequest billDetailRequest) throws URISyntaxException {
		return orderService.deleteBillDetail(roomId, billDetailRequest);
	}
	
	@PostMapping(value = Urls.API_ORDER_CHECKOUT)
	public ResponseEntity<?> checkout(@PathVariable int roomId, Authentication authentication) throws URISyntaxException {
		return orderService.checkoutRoom(roomId, authentication);
	}	

}
