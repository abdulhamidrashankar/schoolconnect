package com.schoolconnect.app.controller;

import com.schoolconnect.app.dto.chat.*;
import com.schoolconnect.app.service.ChatService;
import com.schoolconnect.app.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.UUID;

@Controller
public class WebSocketController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Send message to a specific chat room
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload SendMessageRequest request, SimpMessageHeaderAccessor headerAccessor) {
        try {
            Long senderId = getUserIdFromPrincipal(headerAccessor.getUser());
            ChatMessageDTO message = chatService.sendMessage(senderId, request);
            
            // Send to all users in the chat room
            if (request.getChatRoomId() != null) {
                messagingTemplate.convertAndSend("/topic/chatroom/" + request.getChatRoomId(), message);
            }
            
            // Send to specific user for private messages
            if (request.getReceiverId() != null) {
                // Send to both sender and receiver for private messages
                messagingTemplate.convertAndSendToUser(
                    request.getReceiverId().toString(), 
                    "/queue/private", 
                    message
                );
                messagingTemplate.convertAndSendToUser(
                    senderId.toString(), 
                    "/queue/private", 
                    message
                );
            }
            
        } catch (Exception e) {
            // Send error back to sender
            messagingTemplate.convertAndSendToUser(
                getUserIdFromPrincipal(headerAccessor.getUser()).toString(),
                "/queue/errors",
                new ErrorMessage("Failed to send message: " + e.getMessage())
            );
        }
    }

    // Add reaction to message
    @MessageMapping("/chat.addReaction")
    public void addReaction(@Payload ReactionRequest request, SimpMessageHeaderAccessor headerAccessor) {
        try {
            Long userId = getUserIdFromPrincipal(headerAccessor.getUser());
            chatService.addReaction(request.getMessageId(), userId, request.getReaction());
            
            // Notify all users in the chat room about the reaction
            messagingTemplate.convertAndSend("/topic/chatroom/" + request.getChatRoomId(), 
                new ReactionUpdateMessage(request.getMessageId(), request.getReaction(), "ADDED"));
                
        } catch (Exception e) {
            messagingTemplate.convertAndSendToUser(
                getUserIdFromPrincipal(headerAccessor.getUser()).toString(),
                "/queue/errors",
                new ErrorMessage("Failed to add reaction: " + e.getMessage())
            );
        }
    }

    // Remove reaction from message
    @MessageMapping("/chat.removeReaction")
    public void removeReaction(@Payload ReactionRequest request, SimpMessageHeaderAccessor headerAccessor) {
        try {
            Long userId = getUserIdFromPrincipal(headerAccessor.getUser());
            chatService.removeReaction(request.getMessageId(), userId, request.getReaction());
            
            // Notify all users in the chat room about the reaction removal
            messagingTemplate.convertAndSend("/topic/chatroom/" + request.getChatRoomId(), 
                new ReactionUpdateMessage(request.getMessageId(), request.getReaction(), "REMOVED"));
                
        } catch (Exception e) {
            messagingTemplate.convertAndSendToUser(
                getUserIdFromPrincipal(headerAccessor.getUser()).toString(),
                "/queue/errors",
                new ErrorMessage("Failed to remove reaction: " + e.getMessage())
            );
        }
    }

    // Edit message
    @MessageMapping("/chat.editMessage")
    public void editMessage(@Payload EditMessageWebSocketRequest request, SimpMessageHeaderAccessor headerAccessor) {
        try {
            Long userId = getUserIdFromPrincipal(headerAccessor.getUser());
            ChatMessageDTO message = chatService.editMessage(request.getMessageId(), userId, request.getNewBody());
            
            // Notify all users in the chat room about the edit
            messagingTemplate.convertAndSend("/topic/chatroom/" + request.getChatRoomId(), message);
            
        } catch (Exception e) {
            messagingTemplate.convertAndSendToUser(
                getUserIdFromPrincipal(headerAccessor.getUser()).toString(),
                "/queue/errors",
                new ErrorMessage("Failed to edit message: " + e.getMessage())
            );
        }
    }

    // Delete message
    @MessageMapping("/chat.deleteMessage")
    public void deleteMessage(@Payload DeleteMessageRequest request, SimpMessageHeaderAccessor headerAccessor) {
        try {
            Long userId = getUserIdFromPrincipal(headerAccessor.getUser());
            chatService.deleteMessage(request.getMessageId(), userId);
            
            // Notify all users in the chat room about the deletion
            messagingTemplate.convertAndSend("/topic/chatroom/" + request.getChatRoomId(), 
                new MessageDeleteNotification(request.getMessageId()));
                
        } catch (Exception e) {
            messagingTemplate.convertAndSendToUser(
                getUserIdFromPrincipal(headerAccessor.getUser()).toString(),
                "/queue/errors",
                new ErrorMessage("Failed to delete message: " + e.getMessage())
            );
        }
    }

    // Update message status (read, delivered, etc.)
    @MessageMapping("/chat.updateMessageStatus")
    public void updateMessageStatus(@Payload UpdateMessageStatusWebSocketRequest request, SimpMessageHeaderAccessor headerAccessor) {
        try {
            Long userId = getUserIdFromPrincipal(headerAccessor.getUser());
            chatService.updateMessageStatus(request.getMessageId(), request.getStatus(), userId);
            
            // Notify the sender about the status update
            messagingTemplate.convertAndSendToUser(
                request.getSenderId().toString(),
                "/queue/messageStatus",
                new MessageStatusUpdate(request.getMessageId(), request.getStatus())
            );
            
        } catch (Exception e) {
            messagingTemplate.convertAndSendToUser(
                getUserIdFromPrincipal(headerAccessor.getUser()).toString(),
                "/queue/errors",
                new ErrorMessage("Failed to update message status: " + e.getMessage())
            );
        }
    }

    // Typing indicator
    @MessageMapping("/chat.typing")
    public void handleTyping(@Payload TypingIndicatorRequest request, SimpMessageHeaderAccessor headerAccessor) {
        try {
            Long userId = getUserIdFromPrincipal(headerAccessor.getUser());
            
            // Send typing indicator to all users in the chat room except the sender
            messagingTemplate.convertAndSend("/topic/chatroom/" + request.getChatRoomId(), 
                new TypingIndicatorMessage(userId, request.isTyping()));
                
        } catch (Exception e) {
            // Ignore typing indicator errors
        }
    }

    // User online/offline status
    @MessageMapping("/chat.userStatus")
    public void handleUserStatus(@Payload UserStatusRequest request, SimpMessageHeaderAccessor headerAccessor) {
        try {
            Long userId = getUserIdFromPrincipal(headerAccessor.getUser());
            
            // Broadcast user status to all their chat rooms
            notificationService.broadcastUserStatus(userId, request.getStatus());
            
        } catch (Exception e) {
            // Ignore user status errors
        }
    }

    private Long getUserIdFromPrincipal(Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }
        try {
            return Long.valueOf(principal.getName());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid user ID format");
        }
    }

    // Inner classes for WebSocket-specific DTOs
    public static class ReactionRequest {
        private UUID messageId;
        private UUID chatRoomId;
        private String reaction;
        
        public UUID getMessageId() { return messageId; }
        public void setMessageId(UUID messageId) { this.messageId = messageId; }
        public UUID getChatRoomId() { return chatRoomId; }
        public void setChatRoomId(UUID chatRoomId) { this.chatRoomId = chatRoomId; }
        public String getReaction() { return reaction; }
        public void setReaction(String reaction) { this.reaction = reaction; }
    }

    public static class EditMessageWebSocketRequest {
        private UUID messageId;
        private UUID chatRoomId;
        private String newBody;
        
        public UUID getMessageId() { return messageId; }
        public void setMessageId(UUID messageId) { this.messageId = messageId; }
        public UUID getChatRoomId() { return chatRoomId; }
        public void setChatRoomId(UUID chatRoomId) { this.chatRoomId = chatRoomId; }
        public String getNewBody() { return newBody; }
        public void setNewBody(String newBody) { this.newBody = newBody; }
    }

    public static class DeleteMessageRequest {
        private UUID messageId;
        private UUID chatRoomId;
        
        public UUID getMessageId() { return messageId; }
        public void setMessageId(UUID messageId) { this.messageId = messageId; }
        public UUID getChatRoomId() { return chatRoomId; }
        public void setChatRoomId(UUID chatRoomId) { this.chatRoomId = chatRoomId; }
    }

    public static class UpdateMessageStatusWebSocketRequest {
        private UUID messageId;
        private Long senderId;
        private String status;
        
        public UUID getMessageId() { return messageId; }
        public void setMessageId(UUID messageId) { this.messageId = messageId; }
        public Long getSenderId() { return senderId; }
        public void setSenderId(Long senderId) { this.senderId = senderId; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class TypingIndicatorRequest {
        private UUID chatRoomId;
        private boolean typing;
        
        public UUID getChatRoomId() { return chatRoomId; }
        public void setChatRoomId(UUID chatRoomId) { this.chatRoomId = chatRoomId; }
        public boolean isTyping() { return typing; }
        public void setTyping(boolean typing) { this.typing = typing; }
    }

    public static class UserStatusRequest {
        private String status; // ONLINE, OFFLINE, AWAY, BUSY
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class ReactionUpdateMessage {
        private UUID messageId;
        private String reaction;
        private String action; // ADDED, REMOVED
        
        public ReactionUpdateMessage(UUID messageId, String reaction, String action) {
            this.messageId = messageId;
            this.reaction = reaction;
            this.action = action;
        }
        
        public UUID getMessageId() { return messageId; }
        public String getReaction() { return reaction; }
        public String getAction() { return action; }
    }

    public static class MessageDeleteNotification {
        private UUID messageId;
        
        public MessageDeleteNotification(UUID messageId) {
            this.messageId = messageId;
        }
        
        public UUID getMessageId() { return messageId; }
    }

    public static class MessageStatusUpdate {
        private UUID messageId;
        private String status;
        
        public MessageStatusUpdate(UUID messageId, String status) {
            this.messageId = messageId;
            this.status = status;
        }
        
        public UUID getMessageId() { return messageId; }
        public String getStatus() { return status; }
    }

    public static class TypingIndicatorMessage {
        private Long userId;
        private boolean typing;
        
        public TypingIndicatorMessage(Long userId, boolean typing) {
            this.userId = userId;
            this.typing = typing;
        }
        
        public Long getUserId() { return userId; }
        public boolean isTyping() { return typing; }
    }

    public static class ErrorMessage {
        private String message;
        
        public ErrorMessage(String message) {
            this.message = message;
        }
        
        public String getMessage() { return message; }
    }
}
