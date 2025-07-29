package com.schoolconnect.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody; // For OpenAPI docs
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.schoolconnect.app.dto.login.LoginRequest;
import com.schoolconnect.app.dto.login.LoginResponse;

@Tag(name = "Login", description = "Authentication API")
@RequestMapping("/schoolconnect/auth")
public interface LoginControllerAPI {

    @Operation(
        summary = "User Login",
        description = "Authenticate user by username and password and return a login response (e.g. token)",
        requestBody = @RequestBody(
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = LoginRequest.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Login successful",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized - Invalid username or password"
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Forbidden - User is blocked"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "User not found"
            )
        }
    )
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);
}
