package com.karaoke.management.report;

public class BillDetailReport {

	private String typeName;
	private String unitPrice;
	private String number;
	private String price;

	public BillDetailReport(String typeName, String unitPrice, String number, String price) {
		super();
		this.typeName = typeName;
		this.unitPrice = unitPrice;
		this.number = number;
		this.price = price;
	}

	public BillDetailReport() {
		super();
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
