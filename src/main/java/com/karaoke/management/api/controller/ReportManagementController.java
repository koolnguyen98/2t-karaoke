package com.karaoke.management.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.karaoke.management.Urls;
import com.karaoke.management.api.request.ReportRequest;
import com.karaoke.management.service.ReportService;

@RestController
public class ReportManagementController {
	
	@Autowired
	ReportService reportService;

	@GetMapping(value = Urls.API_REPORT_FROM_TO)
	public ResponseEntity<?> reportFromTo(@RequestBody ReportRequest reportRequest){
		return reportService.createReportFromTo(reportRequest.getFromDate(), reportRequest.getToDate());
	}
	
	
}
