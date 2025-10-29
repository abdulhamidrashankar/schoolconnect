package com.schoolconnect.app.dto.chat;

import java.util.Map;
import java.util.UUID;

public class SendMessageRequest {
    private UUID chatRoomId;
    private Long receiverId;
    private String contentType;
    private String body;
    private Map<String, Object> metadata;
    private UUID replyTo;
    
    public SendMessageRequest() {}
    
    public UUID getChatRoomId() { return chatRoomId; }
    public void setChatRoomId(UUID chatRoomId) { this.chatRoomId = chatRoomId; }
    public Long getReceiverId() { return receiverId; }
    public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    public UUID getReplyTo() { return replyTo; }
    public void setReplyTo(UUID replyTo) { this.replyTo = replyTo; }
}