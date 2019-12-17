package com.karaoke.management.report;

import java.util.ArrayList;

public class BillReportMetaData {

	private String startDate;
	private String endDate;
	
	private String roomName;
	
	private String billId;
	
	private ArrayList<BillDetailReport>  billDetailReports;
	
	private String totalPrice;

	private String seller;

	public BillReportMetaData(String startDate, String endDate, String roomName, String billId,
			ArrayList<BillDetailReport> billDetailReports, String seller, String totalPrice) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.roomName = roomName;
		this.billId = billId;
		this.billDetailReports = billDetailReports;
		this.seller = seller;
		this.totalPrice = totalPrice;
	}

	public BillReportMetaData() {
		super();
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public ArrayList<BillDetailReport> getBillDetailReports() {
		return billDetailReports;
	}

	public void setBillDetailReports(ArrayList<BillDetailReport> billDetailReports) {
		this.billDetailReports = billDetailReports;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	
}
