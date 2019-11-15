package com.karaoke.management.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "loaiphong")
public class RoomType {
	
	@Id
    @GeneratedValue
    @Column(name = "maloai")
	int typeId;
	
	@NotNull
	@Size(max = 65)
	@Column(name = "tenloai", nullable = false)
	String typeName;
	
	@NotNull
	@Column(name = "giatien", nullable = false)
	int price;
	
	@OneToMany(mappedBy="roomType",cascade=CascadeType.ALL, fetch = FetchType.LAZY)    
	private List<Room> room = new ArrayList<Room>();

	public RoomType(@NotNull String typeName, @NotNull int price) {
		super();
		this.typeName = typeName;
		this.price = price;
	}

	public RoomType() {
		super();
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
		this.typeName = typeName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<Room> getRoom() {
		return room;
	}

	public void setRoom(List<Room> room) {
		this.room = room;
	}
	
}
