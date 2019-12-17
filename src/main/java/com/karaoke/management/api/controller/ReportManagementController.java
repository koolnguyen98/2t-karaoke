package com.karaoke.management.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.karaoke.management.api.Urls;
import com.karaoke.management.api.request.ReportRequest;
import com.karaoke.management.api.response.ApiResponse;
import com.karaoke.management.service.ReportService;

@RestController
public class ReportManagementController {
	
	@Autowired
	ReportService reportService;

	@PostMapping(value = Urls.API_REPORT_FROM_TO)
	public ResponseEntity<?> reportFromTo(@RequestBody ReportRequest reportRequest, HttpServletRequest request){
		boolean checkNullFromDate = reportRequest.getFromDate() == null;
		boolean checkNullToDate = reportRequest.getToDate() == null;
		if(!checkNullFromDate && !checkNullToDate) {
			return reportService.createReportFromTo(reportRequest.getFromDate(), reportRequest.getToDate(), request);
		}
		return new ResponseEntity<Object>(new ApiResponse(false, "Report Service: from date or to date != null"),
				HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(value = Urls.API_REPORT_DAY)
	public ResponseEntity<?> reportDay(@RequestBody ReportRequest reportRequest, HttpServletRequest request){
		boolean checkNullDayReport = reportRequest.getDayReport() == null;
		if(!checkNullDayReport) {
			return reportService.createReportDay(reportRequest.getDayReport(), request);
		}
		return new ResponseEntity<Object>(new ApiResponse(false, "Report Service: day report != null"),
				HttpStatus.BAD_REQUEST);
	}
		
	@GetMapping(value = Urls.API_REPORT_MONTH)
	public ResponseEntity<?> reportMonth(@RequestBody ReportRequest reportRequest, HttpServletRequest request){
		boolean checkNullDayReport = reportRequest.getDayReport() == null;
		if(!checkNullDayReport) {
			return reportService.createReportMonth(reportRequest.getDayReport(), request);
		}
		return new ResponseEntity<Object>(new ApiResponse(false, "Report Service: day report != null"),
				HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(value = Urls.API_REPORT_YEAR)
	public ResponseEntity<?> reportYear(@RequestBody ReportRequest reportRequest, HttpServletRequest request){
		boolean checkNullDayReport = reportRequest.getDayReport() == null;
		if(!checkNullDayReport) {
			return reportService.createReportYear(reportRequest.getDayReport(), request);
		}
		return new ResponseEntity<Object>(new ApiResponse(false, "Report Service: day report != null"),
				HttpStatus.BAD_REQUEST);
	}
	
}
