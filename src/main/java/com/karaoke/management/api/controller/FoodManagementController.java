package com.karaoke.management.api.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karaoke.management.api.Urls;
import com.karaoke.management.api.request.FoodRequest;
import com.karaoke.management.api.response.ApiResponse;
import com.karaoke.management.reponsitory.FoodRepository;
import com.karaoke.management.service.FoodService;

@RestController
public class FoodManagementController {

	@Autowired
	FoodRepository foodRepository;

	@Autowired
	FoodService foodService;

	@GetMapping(value = Urls.API_FOOD_FIND_ALL)
	public ResponseEntity<?> findAll(HttpServletRequest request) {
		return foodService.findAll(request);
	}

	@GetMapping(value = Urls.API_FOOD_FIND_BY_ID)
	public ResponseEntity<?> findRoomById(@PathVariable int id, HttpServletRequest request) {
		return foodService.findById(id, request);
	}

	@PutMapping(value = Urls.API_FOOD_UPDATE_BY_ID)
	public ResponseEntity<?> updatefood(@RequestParam("food") String food, @PathVariable int id, HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) {
		FoodRequest foodRequest = null;
		try {
			foodRequest = new ObjectMapper().readValue(food, FoodRequest.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return foodService.updateById(id, foodRequest, request, file);
	}

	@PostMapping(value = Urls.API_FOOD_DELETE_BY_ID)
	public ResponseEntity<?> deletefood(@PathVariable int id, HttpServletRequest request) {
		return foodService.deleteById(id, request);

	}

	@PostMapping(value = Urls.API_FOOD_CREATE)
	public ResponseEntity<?> create(@Valid @RequestParam("food") String food, HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) throws URISyntaxException {
		FoodRequest foodRequest = null;
		try {
			foodRequest = new ObjectMapper().readValue(food, FoodRequest.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(foodRequest != null) {
			return foodService.create(foodRequest, request, file);
		}
		return new ResponseEntity<Object>(new ApiResponse(false, "Please fill all information"), HttpStatus.BAD_REQUEST);
	}
	
}
