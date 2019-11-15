package com.karaoke.management.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BillDetailsKey implements Serializable {

	@Column(name = "mahoadon")
	int billId;

	@Column(name = "mathucdon")
	int menuId;

	public BillDetailsKey(int billId, int menuId) {
		super();
		this.billId = billId;
		this.menuId = menuId;
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

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + billId;
		result = prime * result + menuId;
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
		if (menuId != other.menuId)
			return false;
		return true;
	}
	
	
}
