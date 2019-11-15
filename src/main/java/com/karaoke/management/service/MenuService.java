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

import com.karaoke.management.api.request.MenuRequest;
import com.karaoke.management.api.response.ApiResponse;
import com.karaoke.management.api.response.MenuResponse;
import com.karaoke.management.api.response.MessageResponse;
import com.karaoke.management.entity.Menu;
import com.karaoke.management.reponsitory.BillDetailsRepository;
import com.karaoke.management.reponsitory.MenuRepository;

@Service
public class MenuService {

	@Autowired
	MenuRepository menuRepository;

	@Autowired
	BillDetailsRepository billDetailsRepository;

	public ResponseEntity<?> findAll() {
		try {
			List<Menu> listMenu = menuRepository.findAll();
			List<MenuResponse> listMenuResponses = new ArrayList<MenuResponse>();
			for (Menu menu : listMenu) {
				MenuResponse menuResponse = new MenuResponse(menu.getMenuId(), menu.getEatingName(), menu.getUnit(),
						menu.getPrice());
				listMenuResponses.add(menuResponse);
			}

			return ResponseEntity.ok(listMenuResponses);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> findById(int id) {
		try {
			Menu menu = menuRepository.findByMenuId(id);
	    	if (menu == null) {
	    		return new ResponseEntity<Object>(new ApiResponse(false, "Dishes doesn't exist!"), HttpStatus.NOT_FOUND);
	        } else {
		    	MenuResponse menuResponse = new MenuResponse(menu.getMenuId(), menu.getEatingName(), menu.getUnit(), menu.getPrice());
		    	return ResponseEntity.ok(menuResponse);
	        }
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}
	
	public ResponseEntity<?> updateById(int id, MenuRequest menuRequest) {
		try {
			Menu menu = menuRepository.findByMenuId(id);
	        if (menu == null) {
	        	return new ResponseEntity<Object>(new ApiResponse(false, "Dishes doesn't exist!"), HttpStatus.NOT_FOUND);
	        } else {
	        	if(menuRepository.existsByEatingName(menuRequest.getEatingName()) || menuRequest.getEatingName() == "" || menuRequest.getEatingName() == null) {
		            return new ResponseEntity<Object>(new ApiResponse(false, "Eating name is already taken!"),
		                    HttpStatus.BAD_REQUEST);
		        }
		    	
		    	if(menuRequest.getUnit() == "" || menuRequest.getUnit() == null) {
		            return new ResponseEntity<Object>(new ApiResponse(false, "Unit not null!"),
		                    HttpStatus.BAD_REQUEST);
		        }
		    	
		    	if(menuRequest.getPrice() < 0 || menuRequest.getPrice() == 0) {
		            return new ResponseEntity<Object>(new ApiResponse(false, "Price > 0!"),
		                    HttpStatus.BAD_REQUEST);
		        }
		    	Menu updatedMenu = updateMenuById(menu, menuRequest);
	        	MenuResponse menuResponse = new MenuResponse(updatedMenu.getMenuId(), 
	        			updatedMenu.getEatingName(), 
	        			updatedMenu.getUnit(), 
	        			updatedMenu.getPrice());
	            return ResponseEntity.ok(menuResponse);
	        }
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> deleteById(int id) {
		try {
			boolean deleteMenu = deleteMenuById(id);
	    	MessageResponse messageResponse = null;
	    	if (!deleteMenu) {
	    		messageResponse = new MessageResponse("Dishes doesn't exist!", 404);
	    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
			}
	    	
	    	messageResponse = new MessageResponse("Delete Menu Access", 200);
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}
	
	public ResponseEntity<?> create(MenuRequest menuRequest) {
		try {
			if(menuRepository.existsByEatingName(menuRequest.getEatingName()) || menuRequest.getEatingName() == "" || menuRequest.getEatingName() == null) {
	            return new ResponseEntity<Object>(new ApiResponse(false, "Eating name is already taken!"),
	                    HttpStatus.BAD_REQUEST);
	        }
	    	
	    	if(menuRequest.getUnit() == "" || menuRequest.getUnit() == null) {
	            return new ResponseEntity<Object>(new ApiResponse(false, "Unit not null!"),
	                    HttpStatus.BAD_REQUEST);
	        }
	    	
	    	if(menuRequest.getPrice() < 0 || menuRequest.getPrice() == 0) {
	            return new ResponseEntity<Object>(new ApiResponse(false, "Price > 0!"),
	                    HttpStatus.BAD_REQUEST);
	        }
	    	
	        Menu createdMenu = createRoom(menuRequest);
	        if (createdMenu == null) {
	        	return new ResponseEntity<Object>(new ApiResponse(false, "Cann't creat dishes!"), HttpStatus.NOT_FOUND);
	        } else {
	            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
	              .path("/{id}")
	              .buildAndExpand(createdMenu.getMenuId())
	              .toUri();
	            MenuResponse menuResponse = new MenuResponse(createdMenu.getMenuId(), 
	            		createdMenu.getEatingName(), 
	            		createdMenu.getUnit(), 
	            		createdMenu.getPrice());
	            ResponseEntity.created(uri)
	              .body(menuResponse);
				return ResponseEntity.created(uri)
			              .body(menuResponse);
	        }
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

	private Menu createRoom(@Valid MenuRequest menuRequest) {
		Menu menu = menuRepository.findByEatingName(menuRequest.getEatingName());
		if (menu == null) {

			Menu createdMenu = new Menu(menuRequest.getEatingName(), menuRequest.getUnit(), menuRequest.getPrice());
			Menu result = menuRepository.save(createdMenu);

			return result;
		}
		return null;
	}

	private boolean deleteMenuById(int id) {
		Menu menu = menuRepository.findByMenuId(id);

		if (menu != null) {
			boolean checkExitBillDetail = billDetailsRepository.existsByMenu(menu);

			if (!checkExitBillDetail) {
				menuRepository.delete(menu);
				return !checkExitBillDetail;
			}

			return !checkExitBillDetail;

		}

		return false;
	}

	private Menu updateMenuById(Menu menu, MenuRequest menuRequest) {
		
		if (menuRequest.getEatingName() != null && menuRequest.getEatingName() != "") {
			menu.setEatingName(menuRequest.getEatingName());
		}
		if (menuRequest.getUnit() != null && menuRequest.getUnit() != "") {
			menu.setUnit(menuRequest.getUnit());
		}
		if (menuRequest.getPrice() != -1) {
			menu.setPrice(menuRequest.getPrice());
		}
		Menu result = menuRepository.save(menu);

		return result;
	}

}
