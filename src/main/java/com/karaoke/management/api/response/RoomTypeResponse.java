package com.karaoke.management.api.response;

public class RoomTypeResponse implements Response {

	int typeId;
	String typeName;
	int price;
	
	public RoomTypeResponse(int typeId, String typeName, int price) {
		super();
		this.typeId = typeId;
		this.typeName = typeName.trim();
		this.price = price;
	}
	
	public int getTypeId() {
		return typeId;
	}
	
	public void setTypeId(int typeId) {
		this.typeId = typeId;
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
