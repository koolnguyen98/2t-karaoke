package com.karaoke.management.api.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RoomTypeRequest {

	@NotBlank
    @Size(max = 100)
	String typeName;
	int price;
	
	public RoomTypeRequest(String typeName, int price) {
		super();
		this.typeName = typeName.trim();
		this.price = price;
	}
	
	public RoomTypeRequest() {
		super();
	}

	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName.trim();
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
}
