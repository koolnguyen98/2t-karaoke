package com.karaoke.management.api.controller;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.karaoke.management.api.Urls;
import com.karaoke.management.api.request.FoodRequest;
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
	public ResponseEntity<?> updatefood(@RequestBody FoodRequest foodRequest, @PathVariable int id, HttpServletRequest request) {
		return foodService.updateById(id, foodRequest, request);
	}

	@PostMapping(value = Urls.API_FOOD_DELETE_BY_ID)
	public ResponseEntity<?> deletefood(@PathVariable int id, HttpServletRequest request) {
		return foodService.deleteById(id, request);

	}

	@PostMapping(value = Urls.API_FOOD_CREATE)
	public ResponseEntity<?> create(@Valid @RequestBody FoodRequest foodRequest, HttpServletRequest request) throws URISyntaxException {
		return foodService.create(foodRequest, request);
	}
}
