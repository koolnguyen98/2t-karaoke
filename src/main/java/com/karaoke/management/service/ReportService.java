package com.karaoke.management.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.karaoke.management.api.response.BillReport;
import com.karaoke.management.api.response.Report;
import com.karaoke.management.entity.Bill;
import com.karaoke.management.reponsitory.BillRepository;

@Service
public class ReportService {
	
	@Autowired
	BillRepository billRepository;

	public ResponseEntity<?> createReportFromTo(LocalDateTime fromDate, LocalDateTime toDate) {
		
		List<Bill> bills = new ArrayList<Bill>();
		List<BillReport> billReports = new ArrayList<BillReport>();
		LocalDateTime tmpDate = fromDate.plusDays(1);
		while(tmpDate.compareTo(toDate) >= 0) {
			bills = getBillFromTo(tmpDate, toDate);
			
			BillReport billReport = createBillReport(bills);
			
			billReports.add(billReport);
		}
		
		Report report = new Report();
		
		report.setFromDate(fromDate);
		report.setToDate(toDate);
		report.setTotalBill(countTotalBillOnBillReports(billReports));
		report.setBillReports(billReports);
		report.setTotal(countTotalBillReports(billReports));
		
		return ResponseEntity.ok(report);
	}

	private int countTotalBillOnBillReports(List<BillReport> billReports) {
		int count = 0;
		for (BillReport billReport : billReports) {
			count += billReport.getBills().size();
		}
		return count;
	}

	private BillReport createBillReport(List<Bill> bills) {
		BillReport billReport = new BillReport();
		billReport.setBills(bills);
		billReport.setTotalBillInDay(bills.size());
		billReport.setTotalInDay(countTotalInDay(bills));
		return billReport;
	}

	private double countTotalInDay(List<Bill> bills) {
		double total = 0;
		for (Bill bill : bills) {
			total += bill.getTotal();
		}
		return total;
	}

	private double countTotalBillReports(List<BillReport> billReports) {
		double total = 0;
		for (BillReport billReport : billReports) {
			List<Bill> bills = billReport.getBills();
			for (Bill bill : bills) {
				total += bill.getTotal();
			}
		}
		return total;
	}

	private List<Bill> getBillFromTo(LocalDateTime fromDate, LocalDateTime toDate) {
		List<Bill> bills = billRepository.findBillFromTo(fromDate, toDate);
		return bills;
	}

}
