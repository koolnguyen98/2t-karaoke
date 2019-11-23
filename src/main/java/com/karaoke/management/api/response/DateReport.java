package com.karaoke.management.api.response;

import java.time.LocalDateTime;
import java.util.List;

import com.karaoke.management.entity.Bill;

public class DateReport {
	
	LocalDateTime startDate;
	
	LocalDateTime endDate;
	
	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	int totalBillInDay;
	
	double totalInDay;
	
	List<Bill> bills;
	
	public DateReport(LocalDateTime startDate, LocalDateTime endDate, int totalBillInDay, double totalInDay,
			List<Bill> bills) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.totalBillInDay = totalBillInDay;
		this.totalInDay = totalInDay;
		this.bills = bills;
	}

	public DateReport(List<Bill> bills, int totalBillInDay, double totalInDay) {
		super();
		this.bills = bills;
		this.totalBillInDay = totalBillInDay;
		this.totalInDay = totalInDay;
	}
	
	public DateReport() {
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
