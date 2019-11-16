package com.karaoke.management.api.request;

public class BillDetailRequest {
	
	int billId;
	int foodId;
	int number;
	
	public BillDetailRequest(int billId, int foodId, int number, double unitPrice) {
		super();
		this.billId = billId;
		this.foodId = foodId;
		this.number = number;
	}

	public BillDetailRequest() {
		super();
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
