package com.karaoke.management.api.request;

public class RoomRequest {

	String roomName;
	 
	int roomTypeId = -1;
	
	int  status = -1;

	public RoomRequest(String roomName, int roomTypeId, int status) {
		super();
		this.roomName = roomName;
		this.roomTypeId = roomTypeId;
		this.status = status;
	}
	
	public RoomRequest() {
		super();
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
