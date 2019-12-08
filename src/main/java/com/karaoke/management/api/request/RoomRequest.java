package com.karaoke.management.api.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RoomRequest {

	@NotBlank
    @Size(max = 100)
	String roomName;
	 
	int roomTypeId = -1;
	
	int  status = -1;

	public RoomRequest(String roomName, int roomTypeId, int status) {
		super();
		this.roomName = roomName.trim();
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
		this.roomName = roomName.trim();
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
