package com.karaoke.management.api.controller;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karaoke.management.api.Urls;
import com.karaoke.management.service.ImportDatabaseService;

@RestController
public class ImportDatabaseController {
	
	@Autowired
	ImportDatabaseService importDatabaseService;

	@GetMapping(value = Urls.API_IMPORT_DATABASE)
	public ResponseEntity<?> importDatabase(HttpServletRequest request) throws URISyntaxException {
		return importDatabaseService.improtDataBase(request);
	}
	
}
