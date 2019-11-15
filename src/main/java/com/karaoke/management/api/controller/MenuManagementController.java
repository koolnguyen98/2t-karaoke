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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karaoke.management.api.request.MenuRequest;
import com.karaoke.management.reponsitory.MenuRepository;
import com.karaoke.management.service.MenuService;

@RestController
@RequestMapping("/api/menu")
public class MenuManagementController {

    @Autowired
    MenuRepository menuRepository;
    
    @Autowired
    MenuService menuService;
    
    @GetMapping(value={""})
    public ResponseEntity<?> findAll() {
    	return menuService.findAll();
    }
    
    @GetMapping(value={"/{id}"})
    public ResponseEntity<?> findRoomById(@PathVariable int id) {
    	return menuService.findById(id);
    }
    
    @PutMapping(value={"/{id}/update"})
    public ResponseEntity<?> updateMenu(@RequestBody MenuRequest menuRequest, @PathVariable int id) {
        return menuService.updateById(id, menuRequest);
    }
    
    @DeleteMapping(value={"/{id}/delete"})
    public ResponseEntity<?> deleteMenu(@PathVariable int id) {
    	return menuService.deleteById(id);
    	
    }
    
    @PostMapping(value={"/create"})
    public ResponseEntity<?> create(@Valid @RequestBody MenuRequest menuRequest) throws URISyntaxException {
    	return menuService.create(menuRequest);
    }
}
