package com.schoolconnect.app.service;

import com.schoolconnect.app.entity.ChatRoom;
import com.schoolconnect.app.entity.User;
import com.schoolconnect.app.repository.ChatRoomUserRepository;
import com.schoolconnect.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatRoomUserRepository chatRoomUserRepository;

    @Autowired
    private UserRepository userRepository;

    public void broadcastUserStatus(Long userId, String status) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) return;

            // Get all chat rooms where this user is a member
            List<ChatRoom> userChatRooms = chatRoomUserRepository.findChatRoomsByUserId(userId);

            // Broadcast status to all chat rooms
            for (ChatRoom chatRoom : userChatRooms) {
                messagingTemplate.convertAndSend("/topic/chatroom/" + chatRoom.getId(), 
                    new UserStatusNotification(userId, user.getUsername(), status));
            }

            // Also broadcast to general user status topic
            messagingTemplate.convertAndSend("/topic/user-status", 
                new UserStatusNotification(userId, user.getUsername(), status));

        } catch (Exception e) {
            System.err.println("Error broadcasting user status: " + e.getMessage());
        }
    }

    public void sendNotificationToUser(Long userId, String type, Object data) {
        try {
            messagingTemplate.convertAndSendToUser(
                userId.toString(),
                "/queue/notifications",
                new NotificationMessage(type, data)
            );
        } catch (Exception e) {
            System.err.println("Error sending notification to user " + userId + ": " + e.getMessage());
        }
    }

    public void sendNotificationToChatRoom(UUID chatRoomId, String type, Object data) {
        try {
            messagingTemplate.convertAndSend("/topic/chatroom/" + chatRoomId, 
                new NotificationMessage(type, data));
        } catch (Exception e) {
            System.err.println("Error sending notification to chat room " + chatRoomId + ": " + e.getMessage());
        }
    }

    public void sendSystemMessage(UUID chatRoomId, String message) {
        try {
            messagingTemplate.convertAndSend("/topic/chatroom/" + chatRoomId, 
                new SystemMessage(message));
        } catch (Exception e) {
            System.err.println("Error sending system message to chat room " + chatRoomId + ": " + e.getMessage());
        }
    }

    // Inner classes for notification messages
    public static class UserStatusNotification {
        private Long userId;
        private String username;
        private String status;
        private long timestamp = System.currentTimeMillis();

        public UserStatusNotification(Long userId, String username, String status) {
            this.userId = userId;
            this.username = username;
            this.status = status;
        }

        public Long getUserId() { return userId; }
        public String getUsername() { return username; }
        public String getStatus() { return status; }
        public long getTimestamp() { return timestamp; }
    }

    public static class NotificationMessage {
        private String type;
        private Object data;
        private long timestamp = System.currentTimeMillis();

        public NotificationMessage(String type, Object data) {
            this.type = type;
            this.data = data;
        }

        public String getType() { return type; }
        public Object getData() { return data; }
        public long getTimestamp() { return timestamp; }
    }

    public static class SystemMessage {
        private String message;
        private String type = "SYSTEM";
        private long timestamp = System.currentTimeMillis();

        public SystemMessage(String message) {
            this.message = message;
        }

        public String getMessage() { return message; }
        public String getType() { return type; }
        public long getTimestamp() { return timestamp; }
    }
}
