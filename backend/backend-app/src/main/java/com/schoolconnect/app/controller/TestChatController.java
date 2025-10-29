package com.schoolconnect.app.controller;

import com.schoolconnect.app.dto.chat.UserDTO;
import com.schoolconnect.app.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestChatController {

    @Autowired
    private ChatService chatService;

    // Test endpoint to get available users without authentication
    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<List<UserDTO>> getTestUsers(@RequestParam(defaultValue = "1") Long userId) {
        try {
            List<UserDTO> users = chatService.getAvailableUsers(userId);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            // Return empty list if there's an error
            return ResponseEntity.ok(List.of());
        }
    }

    // Test endpoint to check if service is working
    @GetMapping(value = "/health", produces = "application/json")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("{\"status\": \"OK\", \"message\": \"Test controller is working\"}");
    }
}

