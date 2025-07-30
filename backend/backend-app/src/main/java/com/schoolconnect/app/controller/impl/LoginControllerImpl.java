package com.schoolconnect.app.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.schoolconnect.app.controller.LoginControllerAPI;
import com.schoolconnect.app.dto.login.LoginRequest;
import com.schoolconnect.app.dto.login.LoginResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Login", description = "Authentication API") // Required again for Swagger to recognize
public class LoginControllerImpl implements LoginControllerAPI {

	private final LoginService loginService;

	// Constructor injection enforces setting the final field
	public LoginControllerImpl(LoginService loginService) {
		this.loginService = loginService;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		try {
	        LoginResponse response = loginService.login(request);
	        return ResponseEntity.ok(response);
	    } catch (UsernameNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                             .body(new LoginResponse("Invalid username or password", null));
	    }
	}
}
