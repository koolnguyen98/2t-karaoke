package com.karaoke.management.api.response;

public class FoodResponse {
	
	int menuId;
	
	String eatingName;
	
	String unit;
	
	double price;
	
	public FoodResponse() {
		super();
	}

	public FoodResponse(int menuId, String eatingName, String unit, double price) {
		super();
		this.menuId = menuId;
		this.eatingName = eatingName;
		this.unit = unit;
		this.price = price;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
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
