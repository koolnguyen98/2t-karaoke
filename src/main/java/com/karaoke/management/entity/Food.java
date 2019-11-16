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
@Table(name = "food")
public class Food {

	@Id
	@GeneratedValue
	@Column(name = "id")
	int foodId;

	@NotNull
	@Size(max = 65)
	@Column(name = "food_name", nullable = false)
	String eatingName;

	@NotNull
	@Size(max = 20)
	@Column(name = "unit", nullable = false)
	String unit;

	@NotNull
	@Column(name = "price", nullable = false)
	double price;

	@OneToMany(targetEntity=BillDetails.class, mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Bill> bills = new ArrayList<Bill>();

	public Food(@NotNull String eatingName, @NotNull String unit, @NotNull double price) {
		super();
		this.eatingName = eatingName;
		this.unit = unit;
		this.price = price;
	}

	public Food() {
		super();
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
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
