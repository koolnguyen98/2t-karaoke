package com.karaoke.management.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bill")
public class Bill {

	@Id
    @GeneratedValue
    @Column(name = "id")
	int billId;
	
	@NotNull
	@ManyToOne(targetEntity=Room.class)
    @JoinColumn(name="room_id", nullable = false) 
	Room room;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "checkin", nullable = false)
	LocalDateTime  checkin;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "checkout", nullable = false)
	LocalDateTime  checkout;
	
	@Column(name = "total", nullable = false)
	double total;
	
	@Column(name = "detail", nullable = false)
	String details;
	
	@OneToMany(targetEntity=BillDetails.class, mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Food> menus = new ArrayList<Food>();
	
	@NotNull
	@ManyToOne(targetEntity=UserAccount.class)
    @JoinColumn(name="cashier", referencedColumnName = "id", nullable = false) 
	UserAccount userAccount;
	
	

	public Bill(@NotNull Room room, @NotNull LocalDateTime  checkin, @NotNull LocalDateTime  checkout,
			@NotNull double total, @NotNull String details, @NotNull UserAccount userAccount) {
		super();
		this.room = room;
		this.checkin = checkin;
		this.checkout = checkout;
		this.total = total;
		this.details = details;
		this.userAccount = userAccount;
	}
	
	public Bill(@NotNull Room room, @NotNull LocalDateTime  checkin, @NotNull LocalDateTime  checkout,
			@NotNull double total, @NotNull String details) {
		super();
		this.room = room;
		this.checkin = checkin;
		this.checkout = checkout;
		this.total = total;
		this.details = details;
	}
	
	public Bill(@NotNull Room room, @NotNull LocalDateTime  checkin, @NotNull UserAccount userAccount) {
		super();
		this.room = room;
		this.checkin = checkin;
		this.userAccount = userAccount;
	}

	public Bill() {
		super();
	}



	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}
	
	@JsonIgnore
	public Room getRoom() {
		return room;
	}

	public void setRoomId(Room room) {
		this.room = room;
	}

	public LocalDateTime  getCheckin() {
		return checkin;
	}

	public void setCheckin(LocalDateTime  checkin) {
		this.checkin = checkin;
	}

	public LocalDateTime  getChecout() {
		return checkout;
	}

	public void setCheckout(LocalDateTime  checkout) {
		this.checkout = checkout;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	@JsonIgnore
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccoutId(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public List<Food> getMenus() {
		return menus;
	}

	public void setMenus(List<Food> menus) {
		this.menus = menus;
	}
	
	public void addMenu(Food menu) {
		this.menus.add(menu);
	}
	
	
	
}
