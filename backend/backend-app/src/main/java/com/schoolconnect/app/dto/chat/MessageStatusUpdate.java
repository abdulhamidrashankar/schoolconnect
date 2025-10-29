package com.schoolconnect.app.dto.chat;

import java.util.UUID;

public class MessageStatusUpdate {
    private UUID messageId;
    private String status;
    
    public MessageStatusUpdate() {}
    
    public UUID getMessageId() { return messageId; }
    public void setMessageId(UUID messageId) { this.messageId = messageId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
