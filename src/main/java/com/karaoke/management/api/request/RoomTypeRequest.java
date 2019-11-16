package com.karaoke.management.api.request;

public class RoomTypeRequest {

	String typeName;
	int price;
	
	public RoomTypeRequest(String typeName, int price) {
		super();
		this.typeName = typeName;
		this.price = price;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
}
