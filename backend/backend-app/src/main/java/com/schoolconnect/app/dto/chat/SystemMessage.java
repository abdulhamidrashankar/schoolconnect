package com.schoolconnect.app.dto.chat;

import java.util.UUID;

public class SystemMessage {
    private String type;
    private Long userId;
    private UUID roomId;
    private String message;
    private long timestamp = System.currentTimeMillis();
    
    public SystemMessage() {}
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public UUID getRoomId() { return roomId; }
    public void setRoomId(UUID roomId) { this.roomId = roomId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
