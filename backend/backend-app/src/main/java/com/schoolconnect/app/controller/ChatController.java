package com.schoolconnect.app.controller;

import com.schoolconnect.app.dto.chat.*;
import com.schoolconnect.app.entity.ChatRoom;
import com.schoolconnect.app.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    // Get all chat rooms for a user
    @GetMapping(value = "/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ChatRoom>> getUserChatRooms(Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        List<ChatRoom> chatRooms = chatService.getUserChatRooms(userId);
        return ResponseEntity.ok(chatRooms);
    }

    // Get or create private chat between two users
    @PostMapping(value = "/private", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatRoom> getOrCreatePrivateChat(@RequestBody PrivateChatRequest request, Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        ChatRoom chatRoom = chatService.getOrCreatePrivateChat(userId, request.getOtherUserId());
        return ResponseEntity.ok(chatRoom);
    }

    // Create announcement room (teachers only)
    @PostMapping(value = "/announcement", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatRoom> createAnnouncementRoom(@RequestBody CreateAnnouncementRequest request, Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        ChatRoom chatRoom = chatService.createAnnouncementRoom(userId, request.getName(), request.getParticipantIds());
        return ResponseEntity.ok(chatRoom);
    }

    // Create general chat room
    @PostMapping(value = "/room", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatRoom> createChatRoom(@RequestBody CreateChatRoomRequest request, Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        // Add the creator to the participant list
        request.getParticipantIds().add(userId);
        ChatRoom chatRoom = chatService.createGeneralChatRoom(request.getName(), request.getParticipantIds());
        return ResponseEntity.ok(chatRoom);
    }

    // Send message to chat room
    @PostMapping(value = "/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatMessageDTO> sendMessage(@RequestBody SendMessageRequest request, Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        ChatMessageDTO message = chatService.sendMessage(userId, request);
        return ResponseEntity.ok(message);
    }

    // Get messages from a chat room
    @GetMapping(value = "/rooms/{roomId}/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ChatMessageDTO>> getChatRoomMessages(@PathVariable UUID roomId, Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        List<ChatMessageDTO> messages = chatService.getChatRoomMessages(roomId, userId);
        return ResponseEntity.ok(messages);
    }

    // Get private chat messages between two users
    @GetMapping(value = "/private/{otherUserId}/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ChatMessageDTO>> getPrivateChatMessages(@PathVariable Long otherUserId, Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        List<ChatMessageDTO> messages = chatService.getPrivateChatMessages(userId, otherUserId);
        return ResponseEntity.ok(messages);
    }

    // Update message status (read, delivered, etc.)
    @PutMapping(value = "/messages/{messageId}/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateMessageStatus(@PathVariable UUID messageId, @RequestBody UpdateMessageStatusRequest request, Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        chatService.updateMessageStatus(messageId, request.getStatus(), userId);
        return ResponseEntity.ok().build();
    }

    // Add reaction to message
    @PostMapping(value = "/messages/{messageId}/reactions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addReaction(@PathVariable UUID messageId, @RequestBody AddReactionRequest request, Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        chatService.addReaction(messageId, userId, request.getReaction());
        return ResponseEntity.ok().build();
    }

    // Remove reaction from message
    @DeleteMapping(value = "/messages/{messageId}/reactions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> removeReaction(@PathVariable UUID messageId, @RequestBody RemoveReactionRequest request, Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        chatService.removeReaction(messageId, userId, request.getReaction());
        return ResponseEntity.ok().build();
    }

    // Edit message
    @PutMapping(value = "/messages/{messageId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatMessageDTO> editMessage(@PathVariable UUID messageId, @RequestBody EditMessageRequest request, Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        ChatMessageDTO message = chatService.editMessage(messageId, userId, request.getNewBody());
        return ResponseEntity.ok(message);
    }

    // Delete message
    @DeleteMapping(value = "/messages/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable UUID messageId, Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        chatService.deleteMessage(messageId, userId);
        return ResponseEntity.ok().build();
    }

    // Get chat room details
    @GetMapping(value = "/rooms/{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatRoom> getChatRoom(@PathVariable UUID roomId, Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        ChatRoom chatRoom = chatService.getChatRoom(roomId, userId);
        return ResponseEntity.ok(chatRoom);
    }

    // Get available users for chat
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAvailableUsers(Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        List<UserDTO> users = chatService.getAvailableUsers(userId);
        return ResponseEntity.ok(users);
    }

    private Long getCurrentUserId(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            // For testing purposes, return a default user ID when authentication is disabled
            // In production, you should require proper authentication
            return 1L; // Default user ID for testing
        }
        // Assuming the principal contains user ID - adjust based on your authentication setup
        return Long.valueOf(authentication.getName());
    }
}
