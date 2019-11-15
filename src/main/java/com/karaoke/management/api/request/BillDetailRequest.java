package com.karaoke.management.api.request;

public class BillDetailRequest {
	
	int billId;
	int menuId;
	int number;
	
	public BillDetailRequest(int billId, int menuId, int number, double unitPrice) {
		super();
		this.billId = billId;
		this.menuId = menuId;
		this.number = number;
	}

	public BillDetailRequest() {
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
