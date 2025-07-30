package com.schoolconnect.app.controller.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.schoolconnect.app.dto.login.LoginRequest;
import com.schoolconnect.app.dto.login.LoginResponse;
import com.schoolconnect.app.entity.User;
import com.schoolconnect.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;


    public LoginResponse login(LoginRequest request) {
    	  User user = userRepository.findByUsername(request.getUsername())
    	            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Check if user is blocked
        if (user.getIsblocked() != null && user.getIsblocked() == 1) {
            return new LoginResponse("User is blocked", null);
        }

        // Check password
//        boolean validPassword = passwordEncoder.matches(request.getPassword(), user.getPassword());
        boolean validPassword = request.getPassword().equals(user.getPassword());
        if (!validPassword) {
            return new LoginResponse("Invalid password", null);
        }

        // Update last login
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // (Optional) Generate dummy or JWT token
        String token = "DUMMY_TOKEN"; // Replace with real token generation

        return new LoginResponse("Login successful", token);
    }
}