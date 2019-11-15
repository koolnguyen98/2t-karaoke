package com.karaoke.management.api.response;

import java.util.List;

public class OrderResponse implements Response {
	
	RoomResponse room;
	
	BillResponse bill;
	
	List<BillDetailResponse> listBillDetailResponse;

	public OrderResponse(RoomResponse room, BillResponse bill) {
		super();
		this.room = room;
		this.bill = bill;
	}

	public OrderResponse(RoomResponse room, BillResponse bill, List<BillDetailResponse> listBillDetailResponse) {
		super();
		this.room = room;
		this.bill = bill;
		this.listBillDetailResponse = listBillDetailResponse;
	}

	public OrderResponse() {
		super();
	}

	public RoomResponse getRoom() {
		return room;
	}

	public void setRoom(RoomResponse room) {
		this.room = room;
	}

	public BillResponse getBill() {
		return bill;
	}

	public void setBill(BillResponse bill) {
		this.bill = bill;
	}
	
	public List<BillDetailResponse> getListBillDetailResponse() {
		return listBillDetailResponse;
	}

	public void setListBillDetailResponse(List<BillDetailResponse> listBillDetailResponse) {
		this.listBillDetailResponse = listBillDetailResponse;
	}

	public void addBillDetail(BillDetailResponse billDetailResponse) {
		this.listBillDetailResponse.add(billDetailResponse);
	}
}
