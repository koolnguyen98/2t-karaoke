package com.karaoke.management.api.response;

public class FoodResponse {
	
	int foodId;
	
	String eatingName;
	
	String unit;
	
	double price;
	
	String imageBase64;
	
	public FoodResponse() {
		super();
	}

	public FoodResponse(int foodId, String eatingName, String unit, double price) {
		super();
		this.foodId = foodId;
		this.eatingName = eatingName;
		this.unit = unit;
		this.price = price;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public String getEatingName() {
		return eatingName;
	}

	public void setEatingName(String eatingName) {
		this.eatingName = eatingName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}
	
}
