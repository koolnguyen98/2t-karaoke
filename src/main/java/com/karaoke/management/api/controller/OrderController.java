package com.karaoke.management.api.controller;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karaoke.management.api.request.BillDetailRequest;
import com.karaoke.management.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping(value = { "/checkin/{roomId}"})
	public ResponseEntity<?> checkin(@PathVariable int roomId, Authentication authentication) throws URISyntaxException {
		return orderService.checkinRoom(roomId, authentication);
	}
	
	@GetMapping(value = { "/findBill/{roomId}"})
	public ResponseEntity<?> findBillByRoom(@PathVariable int roomId) throws URISyntaxException {
		return orderService.findBillByRoom(roomId);
	}
	
	@PostMapping(value = { "/addbilldetail/{roomId}"})
	public ResponseEntity<?> addBillDetail(@PathVariable int roomId, @Valid @RequestBody BillDetailRequest billDetailRequest) throws URISyntaxException {
		return orderService.addBillDetailRequest(roomId, billDetailRequest);
	}
	
	@PostMapping(value = { "/addlistbilldetail/{roomId}"})
	public ResponseEntity<?> addListBillDetail(@PathVariable int roomId, @Valid @RequestBody List<BillDetailRequest> listBillDetailRequest) throws URISyntaxException {
		return orderService.addListBillDetailRequest(roomId, listBillDetailRequest);
	}
	
	@PostMapping(value = { "/deletebilldetail/{roomId}"})
	public ResponseEntity<?> deleteBillDetail(@PathVariable int roomId, @Valid @RequestBody BillDetailRequest billDetailRequest) throws URISyntaxException {
		return orderService.deleteBillDetail(roomId, billDetailRequest);
	}
	
	@PostMapping(value = { "/checkout/{roomId}"})
	public ResponseEntity<?> checkout(@PathVariable int roomId, Authentication authentication) throws URISyntaxException {
		return orderService.checkoutRoom(roomId, authentication);
	}	

}
