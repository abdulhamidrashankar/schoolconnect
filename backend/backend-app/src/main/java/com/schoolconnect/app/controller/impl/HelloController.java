package com.schoolconnect.app.controller.impl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Hello", description = "First API")
@RestController
@RequestMapping("/schoolconnect")
public class HelloController {

    @Operation(summary = "Say Hello", description = "Returns a welcome message")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully returned greeting")
    })
    @GetMapping("/health")
    public String checkApplicationHealth() {
        return "Hello, Welcome to School Connect App!"
        		+ "\nThe back end application is running";
    }
}


