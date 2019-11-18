package com.karaoke.management.api.response;

import java.util.List;

import com.karaoke.management.entity.Bill;

public class BillReport {
	
	List<Bill> bills;
	
	int totalBillInDay;
	
	double totalInDay;

	public BillReport(List<Bill> bills, int totalBillInDay, double totalInDay) {
		super();
		this.bills = bills;
		this.totalBillInDay = totalBillInDay;
		this.totalInDay = totalInDay;
	}
	
	public BillReport() {
		super();
	}
	
	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	public int getTotalBillInDay() {
		return totalBillInDay;
	}

	public void setTotalBillInDay(int totalBillInDay) {
		this.totalBillInDay = totalBillInDay;
	}

	public double getTotalInDay() {
		return totalInDay;
	}

	public void setTotalInDay(double totalInDay) {
		this.totalInDay = totalInDay;
	}

}
