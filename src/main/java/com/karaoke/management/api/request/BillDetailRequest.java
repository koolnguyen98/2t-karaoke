package com.karaoke.management.api.request;

public class BillDetailRequest {
	
	int foodId;
	int number;
	
	public BillDetailRequest(int foodId, int number) {
		super();
		this.foodId = foodId;
		this.number = number;
	}

	public BillDetailRequest() {
		super();
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
