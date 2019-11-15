package com.karaoke.management.api.response;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BillResponse implements Response {
	
	int billId;
	
	int roomId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime checkin;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime checkout;
	
	double total;
	
	int casher;

	public BillResponse(int billId, int roomId, LocalDateTime checkin, LocalDateTime checkout, double total, int casher) {
		super();
		this.billId = billId;
		this.roomId = roomId;
		this.checkin = checkin;
		this.checkout = checkout;
		this.total = total;
		this.casher = casher;
	}

	public BillResponse(int billId, int roomId, LocalDateTime checkin, int casher) {
		super();
		this.billId = billId;
		this.roomId = roomId;
		this.checkin = checkin;
		this.casher = casher;
	}
	
	public BillResponse(int billId, int roomId, LocalDateTime checkin, double total, int casher) {
		super();
		this.billId = billId;
		this.roomId = roomId;
		this.checkin = checkin;
		this.total = total;
		this.casher = casher;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public LocalDateTime getCheckin() {
		return checkin;
	}

	public void setCheckin(LocalDateTime checkin) {
		this.checkin = checkin;
	}

	public LocalDateTime getCheckout() {
		return checkout;
	}

	public void setCheckout(LocalDateTime checkout) {
		this.checkout = checkout;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getCasher() {
		return casher;
	}

	public void setCasher(int casher) {
		this.casher = casher;
	}
	
	
}
