package com.epicode.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.epicode.security.entity.User;
import com.epicode.services.UserService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired UserService userService;
	
	@GetMapping("/getUser/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id) {
		User u = userService.getUserById(id);
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}
	
	@GetMapping("/getUsers")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> lista = userService.getAllUsers();
		return new ResponseEntity<List<User>>(lista, HttpStatus.OK);
	}
	
//	@GetMapping("/getUsernameById/{userId}")
//    public ResponseEntity<?> getUsernameById(@PathVariable Long userId) {
//        try {
//            String username = userService.getUsernameById(userId);
//            return ResponseEntity.ok(username);
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore nel recupero dello username");
//        }
//    }
	
	@GetMapping("/getUsernameById/{userId}")
	public ResponseEntity<Map<String, String>> getUsernameById(@PathVariable Long userId) {
	    try {
	        String username = userService.getUsernameById(userId);
	        Map<String, String> response = new HashMap<>();
	        response.put("username", username);
	        return ResponseEntity.ok(response);
	    } catch (EntityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
}
