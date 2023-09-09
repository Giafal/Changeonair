package com.epicode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.epicode.security.entity.User;
import com.epicode.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired UserService userService;
	
	@GetMapping("/getUser/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id) {
		User u = userService.getUserById(id);
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}
}
