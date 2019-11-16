package com.karaoke.management.api.response;

public class BillDetailResponse {

	FoodResponse foodResponse;
	int number;
	double unitPrice;
	
	public BillDetailResponse(FoodResponse foodResponse, int number, double unitPrice) {
		super();
		this.foodResponse = foodResponse;
		this.number = number;
		this.unitPrice = unitPrice;
	}

	public BillDetailResponse() {
		super();
	}

	public FoodResponse getFoodResponse() {
		return foodResponse;
	}

	public void setFoodResponse(FoodResponse foodResponse) {
		this.foodResponse = foodResponse;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
}
