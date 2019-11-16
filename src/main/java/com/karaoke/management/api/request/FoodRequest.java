package com.karaoke.management.api.request;

public class FoodRequest {

	String eatingName;
	
	String unit;
	
	double price = -1;

	public FoodRequest(String eatingName, String unit, double price) {
		super();
		this.eatingName = eatingName;
		this.unit = unit;
		this.price = price;
	}

	public FoodRequest() {
		super();
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
	
}
