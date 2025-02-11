package com.karaoke.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.karaoke.management.api.WriterLog;
import com.karaoke.management.api.request.FoodRequest;
import com.karaoke.management.api.response.ApiResponse;
import com.karaoke.management.api.response.FoodResponse;
import com.karaoke.management.api.response.MessageResponse;
import com.karaoke.management.entity.Bill;
import com.karaoke.management.entity.BillDetails;
import com.karaoke.management.entity.Food;
import com.karaoke.management.reponsitory.BillDetailsRepository;
import com.karaoke.management.reponsitory.BillRepository;
import com.karaoke.management.reponsitory.FoodRepository;
import com.karaoke.management.service.helper.FileStorageService;

@Service
public class FoodService {

	@Autowired
	FoodRepository foodRepository;

	@Autowired
	BillDetailsRepository billDetailsRepository;
	
	@Autowired
	BillRepository billRepository;
	
	@Autowired
    private FileStorageService fileStorageService;
	
	Logger logger = WriterLog.getLogger(FoodService.class.toString());

	public ResponseEntity<?> findAll(HttpServletRequest request) {
		try {
			List<Food> listfood = foodRepository.findAllFood();
			List<FoodResponse> listfoodResponses = new ArrayList<FoodResponse>();
			for (Food food : listfood) {
				FoodResponse foodResponse = new FoodResponse(food.getFoodId(), food.getEatingName(), food.getUnit(),
						food.getPrice());
				foodResponse.setImageBase64(new String(fileStorageService.getImage(food.getFoodId()), "UTF-8"));
				listfoodResponses.add(foodResponse);
			}
			logger.info("Client " + request.getRemoteAddr() + ": " + "Get all food successfully");
			return ResponseEntity.ok(listfoodResponses);

		} catch (Exception e) {
			logger.warning("Client " + request.getRemoteAddr() + ": " + e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> findById(int id, HttpServletRequest request) {
		try {
			Food food = foodRepository.findByFoodId(id);
	    	if (food == null) {
	    		logger.info("Client " + request.getRemoteAddr() + ": " + "Get food by " + id + " unsuccessfully");
	    		return new ResponseEntity<Object>(new ApiResponse(false, "Dishes doesn't exist!"), HttpStatus.NOT_FOUND);
	        } else {
	        	logger.info("Client " + request.getRemoteAddr() + ": " + "Get food by " + id + " successfully");
		    	FoodResponse foodResponse = new FoodResponse(food.getFoodId(), food.getEatingName(), food.getUnit(), food.getPrice());
		    	foodResponse.setImageBase64(new String(fileStorageService.getImage(food.getFoodId()), "UTF-8"));
		    	return ResponseEntity.ok(foodResponse);
	        }
		} catch (Exception e) {
			logger.warning("Client " + request.getRemoteAddr() + ": " + e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}
	
	public ResponseEntity<?> updateById(int id, FoodRequest foodRequest, HttpServletRequest request, MultipartFile file) {
		try {
			Food food = foodRepository.findByFoodId(id);
	        if (food == null) {
	        	logger.info("Client " + request.getRemoteAddr() + ": " + "Update food unsuccessfully");
	        	return new ResponseEntity<Object>(new ApiResponse(false, "Dishes doesn't exist!"), HttpStatus.NOT_FOUND);
	        } else {
	        	if(foodRepository.existsByEatingName(foodRequest.getEatingName(), id).size() != 0|| foodRequest.getEatingName() == "" || foodRequest.getEatingName() == null) {
	        		logger.info("Client " + request.getRemoteAddr() + ": " + "Update food Eating name is already taken!");
	        		return new ResponseEntity<Object>(new ApiResponse(false, "Eating name is already taken!"),
		                    HttpStatus.BAD_REQUEST);
		        }
		    	
		    	if(foodRequest.getUnit() == "" || foodRequest.getUnit() == null) {
		    		logger.info("Client " + request.getRemoteAddr() + ": " + "Update food Unit not null!");
		            return new ResponseEntity<Object>(new ApiResponse(false, "Unit not null!"),
		                    HttpStatus.BAD_REQUEST);
		        }
		    	
		    	if(foodRequest.getPrice() < 0 || foodRequest.getPrice() == 0) {
		    		logger.info("Client " + request.getRemoteAddr() + ": " + "Update food Price > 0!");
		            return new ResponseEntity<Object>(new ApiResponse(false, "Price > 0!"),
		                    HttpStatus.BAD_REQUEST);
		        }
		    	Food updatedfood = updateFoodById(food, foodRequest, file);
	        	FoodResponse foodResponse = new FoodResponse(updatedfood.getFoodId(), 
	        			updatedfood.getEatingName(), 
	        			updatedfood.getUnit(), 
	        			updatedfood.getPrice());
	        	foodResponse.setImageBase64(new String(fileStorageService.getImage(food.getFoodId()), "UTF-8"));
	        	logger.info("Client " + request.getRemoteAddr() + ": " + "Update food successfully!");
	            return ResponseEntity.ok(foodResponse);
	        }
		} catch (Exception e) {
			logger.warning("Client " + request.getRemoteAddr() + ": " + e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> deleteById(int id, HttpServletRequest request) {
		try {
			boolean deletefood = deleteFoodById(id);
	    	MessageResponse messageResponse = null;
	    	if (!deletefood) {
	    		logger.info("Client " + request.getRemoteAddr() + ": " + "Update food by " + id + " unsuccessfully!");
	    		messageResponse = new MessageResponse("Dishes doesn't exist!", 404);
	    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
			}
	    	logger.info("Client " + request.getRemoteAddr() + ": " + "Update food by " + id + " successfully!");
	    	messageResponse = new MessageResponse("Delete food Access", 200);
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (Exception e) {
			logger.warning("Client " + request.getRemoteAddr() + ": " + e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}
	
	public ResponseEntity<?> create(FoodRequest foodRequest, HttpServletRequest request, MultipartFile file) {
		try {
			if(foodRepository.existsByEatingName(foodRequest.getEatingName())) {
				logger.info("Client " + request.getRemoteAddr() + ": " + "Update food Eating name is already taken!");
	            return new ResponseEntity<Object>(new ApiResponse(false, "Eating name is already taken!"),
	                    HttpStatus.BAD_REQUEST);
	        }
	    	
	    	if(foodRequest.getUnit() == "" || foodRequest.getUnit() == null) {
	    		logger.info("Client " + request.getRemoteAddr() + ": " + "Update food Unit not null!");
	            return new ResponseEntity<Object>(new ApiResponse(false, "Unit not null!"),
	                    HttpStatus.BAD_REQUEST);
	        }
	    	
	    	if(foodRequest.getPrice() < 0 || foodRequest.getPrice() == 0) {
	    		logger.info("Client " + request.getRemoteAddr() + ": " + "Update food Price > 0!");
	            return new ResponseEntity<Object>(new ApiResponse(false, "Price > 0!"),
	                    HttpStatus.BAD_REQUEST);
	        }
	    	
	        Food createdfood = createFood(foodRequest, file);
	        if (createdfood == null) {
	        	logger.info("Client " + request.getRemoteAddr() + ": " + "Create food by unsuccessfully!");
	        	return new ResponseEntity<Object>(new ApiResponse(false, "Cann't creat dishes!"), HttpStatus.NOT_FOUND);
	        } else {

	            FoodResponse foodResponse = new FoodResponse(createdfood.getFoodId(), 
	            		createdfood.getEatingName(), 
	            		createdfood.getUnit(), 
	            		createdfood.getPrice());
	            foodResponse.setImageBase64(new String(fileStorageService.getImage(createdfood.getFoodId()), "UTF-8"));
	            logger.info("Client " + request.getRemoteAddr() + ": " + "Create food by successfully!");
				return ResponseEntity.ok(foodResponse);
	        }
		} catch (Exception e) {
			logger.warning("Client " + request.getRemoteAddr() + ": " + e.toString());
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

	private Food createFood(@Valid FoodRequest foodRequest, MultipartFile file) {
		Food food = foodRepository.findByEatingName(foodRequest.getEatingName());
		if (food == null) {
			Food createdfood = new Food(foodRequest.getEatingName(), foodRequest.getUnit(), foodRequest.getPrice());
			Food result = foodRepository.save(createdfood);
			if(file != null) {
				String imageLink = coppyImage(file, result.getFoodId());
				result.setImgLink(imageLink);
			}else {
				result.setImgLink("default.png");
			}
			result = foodRepository.save(result);
			return result;
		}
		return null;
	}

	private String coppyImage(MultipartFile file, int id) {
		String fileName = fileStorageService.storeFile(file, id);

        return fileName;
	}

	private boolean deleteFoodById(int id) {
		Food food = foodRepository.findByFoodId(id);

		if (food != null) {
			boolean checkExitBillDetail = billDetailsRepository.existsByFood(food);

			if (!checkExitBillDetail) {
				foodRepository.delete(food);
			}else {
				List<Bill> checkBillSuccess = billRepository.findBillSuccess();
				for (Bill bill : checkBillSuccess) {
					List<BillDetails> billDetails = billDetailsRepository.findByBill(bill);
					for (BillDetails billDetail : billDetails) {
						if(billDetail.getFood() == food) {
							return false;
						}
					}
				}
				food.setDelete(true);
				foodRepository.save(food);
			}
			return true;
		}
		return false;
	}

	private Food updateFoodById(Food food, FoodRequest foodRequest, MultipartFile file) {
		if (foodRequest.getEatingName() != null && foodRequest.getEatingName() != "") {
			food.setEatingName(foodRequest.getEatingName());
		}
		if (foodRequest.getUnit() != null && foodRequest.getUnit() != "") {
			food.setUnit(foodRequest.getUnit());
		}
		if (foodRequest.getPrice() != -1) {
			food.setPrice(foodRequest.getPrice());
		}
		if (file != null) {
			String imageLink = coppyImage(file, food.getFoodId());
			food.setImgLink(imageLink);
		}
		Food result = foodRepository.save(food);
		return result;
	}

}
