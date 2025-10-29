package com.schoolconnect.app.config.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEventListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    private final Map<String, String> sessionUserMap = new ConcurrentHashMap<>();

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        
        if (headerAccessor.getUser() != null) {
            String userId = headerAccessor.getUser().getName();
            sessionUserMap.put(sessionId, userId);
            
            System.out.println("✅ User connected: " + userId + " (session: " + sessionId + ")");
            
            UserStatusMessage statusMessage = new UserStatusMessage();
            statusMessage.setUserId(Long.parseLong(userId));
            statusMessage.setOnline(true);
            
            messagingTemplate.convertAndSend("/topic/user-status", statusMessage);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        
        String userId = sessionUserMap.remove(sessionId);
        
        if (userId != null) {
            System.out.println("❌ User disconnected: " + userId + " (session: " + sessionId + ")");
            
            UserStatusMessage statusMessage = new UserStatusMessage();
            statusMessage.setUserId(Long.parseLong(userId));
            statusMessage.setOnline(false);
            
            messagingTemplate.convertAndSend("/topic/user-status", statusMessage);
        }
    }

    public static class UserStatusMessage {
        private Long userId;
        private boolean online;
        private long timestamp = System.currentTimeMillis();

        public UserStatusMessage() {}

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public boolean isOnline() { return online; }
        public void setOnline(boolean online) { this.online = online; }
        public long getTimestamp() { return timestamp; }
        public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    }
}