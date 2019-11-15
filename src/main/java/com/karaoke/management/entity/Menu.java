package com.karaoke.management.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "thucdon")
public class Menu {

	@Id
	@GeneratedValue
	@Column(name = "mathucdon")
	int menuId;

	@NotNull
	@Size(max = 65)
	@Column(name = "tenmonan", nullable = false)
	String eatingName;

	@NotNull
	@Size(max = 20)
	@Column(name = "donvitinh", nullable = false)
	String unit;

	@NotNull
	@Column(name = "giatien", nullable = false)
	double price;

	@OneToMany(targetEntity=BillDetails.class, mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Bill> bills = new ArrayList<Bill>();

	public Menu(@NotNull String eatingName, @NotNull String unit, @NotNull double price) {
		super();
		this.eatingName = eatingName;
		this.unit = unit;
		this.price = price;
	}

	public Menu() {
		super();
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getEatingName() {
		return eatingName;
	}

	public void setEatingName(String eatingName) {
		this.eatingName = eatingName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

}
