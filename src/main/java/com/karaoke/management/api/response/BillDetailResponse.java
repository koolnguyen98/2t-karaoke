package com.karaoke.management.api.response;

public class BillDetailResponse {

	MenuResponse menuResponse;
	int number;
	double unitPrice;
	
	public BillDetailResponse(MenuResponse menuResponse, int number, double unitPrice) {
		super();
		this.menuResponse = menuResponse;
		this.number = number;
		this.unitPrice = unitPrice;
	}

	public BillDetailResponse() {
		super();
	}

	public MenuResponse getMenuResponse() {
		return menuResponse;
	}

	public void setMenuResponse(MenuResponse menuResponse) {
		this.menuResponse = menuResponse;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
}
