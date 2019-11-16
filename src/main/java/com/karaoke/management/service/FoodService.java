package com.karaoke.management.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.karaoke.management.api.request.FoodRequest;
import com.karaoke.management.api.response.ApiResponse;
import com.karaoke.management.api.response.FoodResponse;
import com.karaoke.management.api.response.MessageResponse;
import com.karaoke.management.entity.Food;
import com.karaoke.management.reponsitory.BillDetailsRepository;
import com.karaoke.management.reponsitory.FoodRepository;

@Service
public class FoodService {

	@Autowired
	FoodRepository foodRepository;

	@Autowired
	BillDetailsRepository billDetailsRepository;

	public ResponseEntity<?> findAll() {
		try {
			List<Food> listfood = foodRepository.findAll();
			List<FoodResponse> listfoodResponses = new ArrayList<FoodResponse>();
			for (Food food : listfood) {
				FoodResponse foodResponse = new FoodResponse(food.getFoodId(), food.getEatingName(), food.getUnit(),
						food.getPrice());
				listfoodResponses.add(foodResponse);
			}

			return ResponseEntity.ok(listfoodResponses);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> findById(int id) {
		try {
			Food food = foodRepository.findByFoodId(id);
	    	if (food == null) {
	    		return new ResponseEntity<Object>(new ApiResponse(false, "Dishes doesn't exist!"), HttpStatus.NOT_FOUND);
	        } else {
		    	FoodResponse foodResponse = new FoodResponse(food.getFoodId(), food.getEatingName(), food.getUnit(), food.getPrice());
		    	return ResponseEntity.ok(foodResponse);
	        }
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}
	
	public ResponseEntity<?> updateById(int id, FoodRequest foodRequest) {
		try {
			Food food = foodRepository.findByFoodId(id);
	        if (food == null) {
	        	return new ResponseEntity<Object>(new ApiResponse(false, "Dishes doesn't exist!"), HttpStatus.NOT_FOUND);
	        } else {
	        	if(foodRepository.existsByEatingName(foodRequest.getEatingName()) || foodRequest.getEatingName() == "" || foodRequest.getEatingName() == null) {
		            return new ResponseEntity<Object>(new ApiResponse(false, "Eating name is already taken!"),
		                    HttpStatus.BAD_REQUEST);
		        }
		    	
		    	if(foodRequest.getUnit() == "" || foodRequest.getUnit() == null) {
		            return new ResponseEntity<Object>(new ApiResponse(false, "Unit not null!"),
		                    HttpStatus.BAD_REQUEST);
		        }
		    	
		    	if(foodRequest.getPrice() < 0 || foodRequest.getPrice() == 0) {
		            return new ResponseEntity<Object>(new ApiResponse(false, "Price > 0!"),
		                    HttpStatus.BAD_REQUEST);
		        }
		    	Food updatedfood = updateFoodById(food, foodRequest);
	        	FoodResponse foodResponse = new FoodResponse(updatedfood.getFoodId(), 
	        			updatedfood.getEatingName(), 
	        			updatedfood.getUnit(), 
	        			updatedfood.getPrice());
	            return ResponseEntity.ok(foodResponse);
	        }
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> deleteById(int id) {
		try {
			boolean deletefood = deleteFoodById(id);
	    	MessageResponse messageResponse = null;
	    	if (!deletefood) {
	    		messageResponse = new MessageResponse("Dishes doesn't exist!", 404);
	    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
			}
	    	
	    	messageResponse = new MessageResponse("Delete food Access", 200);
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}
	
	public ResponseEntity<?> create(FoodRequest foodRequest) {
		try {
			if(foodRepository.existsByEatingName(foodRequest.getEatingName())) {
	            return new ResponseEntity<Object>(new ApiResponse(false, "Eating name is already taken!"),
	                    HttpStatus.BAD_REQUEST);
	        }
	    	
	    	if(foodRequest.getUnit() == "" || foodRequest.getUnit() == null) {
	            return new ResponseEntity<Object>(new ApiResponse(false, "Unit not null!"),
	                    HttpStatus.BAD_REQUEST);
	        }
	    	
	    	if(foodRequest.getPrice() < 0 || foodRequest.getPrice() == 0) {
	            return new ResponseEntity<Object>(new ApiResponse(false, "Price > 0!"),
	                    HttpStatus.BAD_REQUEST);
	        }
	    	
	        Food createdfood = createFood(foodRequest);
	        if (createdfood == null) {
	        	return new ResponseEntity<Object>(new ApiResponse(false, "Cann't creat dishes!"), HttpStatus.NOT_FOUND);
	        } else {
	            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
	              .path("/{id}")
	              .buildAndExpand(createdfood.getFoodId())
	              .toUri();
	            FoodResponse foodResponse = new FoodResponse(createdfood.getFoodId(), 
	            		createdfood.getEatingName(), 
	            		createdfood.getUnit(), 
	            		createdfood.getPrice());
	            ResponseEntity.created(uri)
	              .body(foodResponse);
				return ResponseEntity.created(uri)
			              .body(foodResponse);
	        }
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

	private Food createFood(@Valid FoodRequest foodRequest) {
		Food food = foodRepository.findByEatingName(foodRequest.getEatingName());
		if (food == null) {

			Food createdfood = new Food(foodRequest.getEatingName(), foodRequest.getUnit(), foodRequest.getPrice());
			Food result = foodRepository.save(createdfood);

			return result;
		}
		return null;
	}

	private boolean deleteFoodById(int id) {
		Food food = foodRepository.findByFoodId(id);

		if (food != null) {
			boolean checkExitBillDetail = billDetailsRepository.existsByFood(food);

			if (!checkExitBillDetail) {
				foodRepository.delete(food);
				return !checkExitBillDetail;
			}

			return !checkExitBillDetail;

		}

		return false;
	}

	private Food updateFoodById(Food food, FoodRequest foodRequest) {
		
		if (foodRequest.getEatingName() != null && foodRequest.getEatingName() != "") {
			food.setEatingName(foodRequest.getEatingName());
		}
		if (foodRequest.getUnit() != null && foodRequest.getUnit() != "") {
			food.setUnit(foodRequest.getUnit());
		}
		if (foodRequest.getPrice() != -1) {
			food.setPrice(foodRequest.getPrice());
		}
		Food result = foodRepository.save(food);

		return result;
	}

}
