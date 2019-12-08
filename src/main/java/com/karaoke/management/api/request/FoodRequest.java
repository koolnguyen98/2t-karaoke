package com.karaoke.management.api.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class FoodRequest {

	@NotBlank
    @Size(max = 100)
	String eatingName;
	
	@NotBlank
    @Size(max = 50)
	String unit;
	
	double price = -1;

	public FoodRequest(String eatingName, String unit, double price) {
		super();
		this.eatingName = eatingName.trim();
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
		this.eatingName = eatingName.trim();
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit.trim();
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
