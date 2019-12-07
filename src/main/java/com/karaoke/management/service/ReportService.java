package com.karaoke.management.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.karaoke.management.api.WriterLog;
import com.karaoke.management.api.response.ApiResponse;
import com.karaoke.management.api.response.DateReport;
import com.karaoke.management.api.response.Report;
import com.karaoke.management.entity.Bill;
import com.karaoke.management.reponsitory.BillRepository;

@Service
public class ReportService {

	@Autowired
	BillRepository billRepository;

	Logger logger = WriterLog.getLogger(ReportService.class.toString());

	public ResponseEntity<?> createReportFromTo(LocalDateTime fromDate, LocalDateTime toDate,
			HttpServletRequest request) {
		try {
			List<Bill> bills = new ArrayList<Bill>();
			List<DateReport> billReports = new ArrayList<DateReport>();
			LocalDateTime startDate = fromDate;
			LocalDateTime endDate = startDate.plusDays(1).minusSeconds(1);
			while (startDate.compareTo(toDate) <= 0) {
				bills = getBillFromTo(startDate, endDate);

				DateReport billReport = createBillReport(bills, startDate, endDate);

				billReports.add(billReport);
				startDate = endDate.plusSeconds(1);
				endDate = startDate.plusDays(1).minusSeconds(1);
			}

			Report report = new Report();

			report.setFromDate(fromDate);
			report.setToDate(toDate);
			report.setTotalBill(countTotalBillOnBillReports(billReports));
			report.setDateReports(billReports);
			report.setTotal(countTotalBillReports(billReports));
			logger.info("Client " + request.getRemoteAddr() + ": " + "Get report from " + fromDate.toString() + " to " + toDate.toString() + " successfully");
			return ResponseEntity.ok(report);

		} catch (Exception e) {
			logger.warning("Client " + request.getRemoteAddr() + ": " + e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> createReportDay(LocalDateTime dayReport, HttpServletRequest request) {
		try {
			LocalDateTime startDate = dayReport;
			LocalDateTime endDate = startDate.plusDays(1).minusSeconds(1);

			List<Bill> bills = new ArrayList<Bill>();
			bills = getBillFromTo(startDate, endDate);

			DateReport billReport = createBillReport(bills, startDate, endDate);
			logger.info("Client " + request.getRemoteAddr() + ": " + "Get report " + dayReport.toString() + " successfully");
			return ResponseEntity.ok(billReport);

		} catch (Exception e) {
			logger.warning("Client " + request.getRemoteAddr() + ": " + e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> createReportMonth(LocalDateTime dayReport, HttpServletRequest request) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime date = LocalDateTime
					.parse(dayReport.getYear() + "-" + dayReport.getMonthValue() + "-01 00:00:00", formatter);
			LocalDateTime fromDate = date;
			LocalDateTime toDate = date.plusMonths(1);
			logger.info("Client " + request.getRemoteAddr() + ": " + "Get report " + dayReport.getMonth().toString() + " successfully");
			return createReportFromTo(fromDate, toDate, request);

		} catch (Exception e) {
			logger.warning("Client " + request.getRemoteAddr() + ": " + e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> createReportYear(LocalDateTime dayReport, HttpServletRequest request) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime date = LocalDateTime.parse(dayReport.getYear() + "-01-01 00:00:00", formatter);
			LocalDateTime fromDate = date;
			LocalDateTime toDate = date.plusYears(1);
			logger.info("Client " + request.getRemoteAddr() + ": " + "Get report " + dayReport.getYear() + " successfully");
			return createReportFromTo(fromDate, toDate, request);

		} catch (Exception e) {
			logger.warning("Client " + request.getRemoteAddr() + ": " + e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	private int countTotalBillOnBillReports(List<DateReport> billReports) {
		int count = 0;
		for (DateReport billReport : billReports) {
			count += billReport.getBills().size();
		}
		return count;
	}

	private DateReport createBillReport(List<Bill> bills, LocalDateTime startDate, LocalDateTime endDate) {
		DateReport billReport = new DateReport();
		billReport.setStartDate(startDate.toString());
		billReport.setEndDate(endDate.toString());
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

	private double countTotalBillReports(List<DateReport> billReports) {
		double total = 0;
		for (DateReport billReport : billReports) {
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
