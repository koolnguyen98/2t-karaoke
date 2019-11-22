package com.karaoke.management.api.controller;

import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	public ResponseEntity<?> findAll() {
		return foodService.findAll();
	}

	@GetMapping(value = Urls.API_FOOD_FIND_BY_ID)
	public ResponseEntity<?> findRoomById(@PathVariable int id) {
		return foodService.findById(id);
	}

	@PutMapping(value = Urls.API_FOOD_UPDATE_BY_ID)
	public ResponseEntity<?> updatefood(@RequestBody FoodRequest foodRequest, @PathVariable int id) {
		return foodService.updateById(id, foodRequest);
	}

	@DeleteMapping(value = Urls.API_FOOD_DELETE_BY_ID)
	public ResponseEntity<?> deletefood(@PathVariable int id) {
		return foodService.deleteById(id);

	}

	@PostMapping(value = Urls.API_FOOD_CREATE)
	public ResponseEntity<?> create(@Valid @RequestBody FoodRequest foodRequest) throws URISyntaxException {
		return foodService.create(foodRequest);
	}
}
