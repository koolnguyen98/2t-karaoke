package com.karaoke.management.api.response;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Report {
	
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime fromDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime toDate;
	
	double total;
	
	int totalBill;
	
	List<DateReport> dateReports;

	public Report(LocalDateTime fromDate, LocalDateTime toDate, double total, int totalBill, List<DateReport> dateReports) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.total = total;
		this.totalBill = totalBill;
		this.dateReports = dateReports;
	}
	
	public Report() {
		super();
	}

	public LocalDateTime getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDateTime fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDateTime getToDate() {
		return toDate;
	}

	public void setToDate(LocalDateTime toDate) {
		this.toDate = toDate;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(int totalBill) {
		this.totalBill = totalBill;
	}

	public List<DateReport> getDateReports() {
		return dateReports;
	}

	public void setDateReports(List<DateReport> dateReports) {
		this.dateReports = dateReports;
	}

}
