package com.epicode.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.epicode.security.entity.User;
import com.epicode.security.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	
	@Autowired UserRepository repo;
	
	
	public User getUserById(Long id) {
		if(!repo.existsById(id)) {
			throw new EntityNotFoundException("User doesn't exists!!!");
		}
		User u = repo.findById(id).get();
		return u;
	}


	public User findByUsername(String username) {
		User u = repo.findByUsername(username);
		return u;
	}
}
