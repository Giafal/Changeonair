package com.epicode.security.service;

import com.epicode.security.payload.LoginDto;
import com.epicode.security.payload.RegisterDto;

public interface AuthService {
    
	String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
    
}
