package com.karaoke.management.api.response;

public class RoomResponse implements Response {

	int roomId;
	
	String roomName;
	 
	RoomTypeResponse roomTypeResponse;
	
	int  status;

	public RoomResponse(int roomId, String roomName, RoomTypeResponse roomTypeResponse, int status) {
		super();
		this.roomId = roomId;
		this.roomName = roomName;
		this.roomTypeResponse = roomTypeResponse;
		this.status = status;
	}
	
	public RoomResponse() {
		super();
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public RoomTypeResponse getRoomTypeResponse() {
		return roomTypeResponse;
	}

	public void setRoomTypeResponse(RoomTypeResponse roomTypeResponse) {
		this.roomTypeResponse = roomTypeResponse;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
