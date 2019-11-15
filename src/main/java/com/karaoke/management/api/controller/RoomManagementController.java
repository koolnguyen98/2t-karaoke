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

import com.karaoke.management.api.request.RoomRequest;
import com.karaoke.management.service.RoomService;

@RestController
@RequestMapping("/api/room")
public class RoomManagementController {
    
    @Autowired
    RoomService roomService;
    
    @PostMapping(value={"/create"})
    public ResponseEntity<Object> create(@Valid @RequestBody RoomRequest roomRequest) throws URISyntaxException {
    	return roomService.createRoom(roomRequest);
    }
    
    @GetMapping(value={""})
    public ResponseEntity<?> findAll() {
    	return roomService.findAll();
    }
    
    @GetMapping(value={"/{id}"})
    public ResponseEntity<?> findRoomById(@PathVariable int id) {
    	return roomService.findById(id);
    }
    
    @PutMapping(value={"/{id}/update"})
    public ResponseEntity<?> updateRoom(@RequestBody RoomRequest roomRequest, @PathVariable int id) {
    	return roomService.updateRoomById(id, roomRequest);
    }
    
    @DeleteMapping(value={"/{id}/delete"})
    public ResponseEntity<?> deleteRoom(@PathVariable int id) {
    	
    	return roomService.deleteRoomById(id);
    	
    }
    
}
