package com.karaoke.management.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "room")
public class Room {
	
	@Id
    @GeneratedValue
    @Column(name = "id")
	int roomId;
	
	@NotNull
	@Size(max = 20)
	@Column(name = "room_name", nullable = false)
	String roomName;
	
	@NotNull
	@ManyToOne()
    @JoinColumn(name="type_id", nullable = false) 
	private RoomType roomType;
	
	@NotNull
	@Column(name = "status", nullable = false)
	int  status;
	
	@Column(name = "is_delete", nullable = false, columnDefinition = "boolean default false")
	private boolean isDelete;
	
	@OneToMany(targetEntity=Bill.class, mappedBy="room",cascade=CascadeType.ALL, fetch = FetchType.LAZY)    
	private List<Bill> bills = new ArrayList<Bill>();

	public Room(@NotNull String roomName, @NotNull RoomType roomType, @NotNull int status) {
		super();
		this.roomName = roomName;
		this.roomType = roomType;
		this.status = status;
	}

	public Room() {
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
	
	@JsonIgnore
	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
}
