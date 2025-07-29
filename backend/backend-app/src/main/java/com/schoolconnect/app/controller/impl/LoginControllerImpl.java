package com.schoolconnect.app.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.schoolconnect.app.controller.LoginControllerAPI;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Login", description = "Authentication API")  // Required again for Swagger to recognize
public class LoginControllerImpl implements LoginControllerAPI {

    @Override
    public ResponseEntity<String> login(String username, String password) {
        // Dummy authentication logic — replace with real auth logic
        if ("admin".equals(username) && "password".equals(password)) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
