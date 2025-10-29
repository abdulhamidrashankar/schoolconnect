package com.schoolconnect.app.dto.chat;


import java.util.UUID;

public class TypingIndicator {
    private Long userId;
    private UUID roomId;
    private boolean typing;
    
    public TypingIndicator() {}
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public UUID getRoomId() { return roomId; }
    public void setRoomId(UUID roomId) { this.roomId = roomId; }
    public boolean isTyping() { return typing; }
    public void setTyping(boolean typing) { this.typing = typing; }
}
