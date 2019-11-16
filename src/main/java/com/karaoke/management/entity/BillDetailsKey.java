package com.karaoke.management.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BillDetailsKey implements Serializable {

	@Column(name = "bill_id")
	private int billId;

	@Column(name = "food_id")
	private int foodId;

	public BillDetailsKey(int billId, int foodId) {
		super();
		this.billId = billId;
		this.foodId = foodId;
	}

	public BillDetailsKey() {
		super();
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + billId;
		result = prime * result + foodId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BillDetailsKey other = (BillDetailsKey) obj;
		if (billId != other.billId)
			return false;
		if (foodId != other.foodId)
			return false;
		return true;
	}
	
	
}
