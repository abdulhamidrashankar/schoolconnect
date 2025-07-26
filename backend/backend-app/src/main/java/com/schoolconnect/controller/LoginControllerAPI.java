package com.schoolconnect.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Login", description = "Authentication API")
@RequestMapping("/schoolconnect/auth")
public interface LoginControllerAPI {

    /**
     * Login
     */
    @PostMapping("/login")
    @Operation(
        summary = "User login", 
        description = "Authenticate user with username and password"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials"),
        @ApiResponse(responseCode = "400", description = "Bad request - missing parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<String> login(
        @RequestParam String username, 
        @RequestParam String password
    );
}
