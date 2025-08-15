package com.schoolconnect.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.schoolconnect.app.dto.login.LoginRequest;
import com.schoolconnect.app.dto.login.LoginResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody; // For OpenAPI docs
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Tag(name = "Login", description = "Authentication API")
@RequestMapping("/schoolconnect/")
public interface LoginControllerAPI {

	@Operation(summary = "User Login", description = "Authenticate user by username and password and return a login response (e.g. token)", requestBody = @RequestBody(content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequest.class))), responses = {
			@ApiResponse(responseCode = "200", description = "Login successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized - Invalid username or password"),
			@ApiResponse(responseCode = "403", description = "Forbidden - User is blocked"),
			@ApiResponse(responseCode = "404", description = "User not found") })
	@ApiResponse(responseCode = "500", description = "Internal server error")
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request);

	@GetMapping("/session-info")
	public ResponseEntity<?> getSessionFromContext(HttpSession session);

}