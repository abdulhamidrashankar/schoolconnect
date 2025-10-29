package com.schoolconnect.app.dto.chat;

import java.util.UUID;

public class ReactionUpdate {
    private UUID messageId;
    private String reaction;
    private boolean added = true;
    
    public ReactionUpdate() {}
    
    public UUID getMessageId() { return messageId; }
    public void setMessageId(UUID messageId) { this.messageId = messageId; }
    public String getReaction() { return reaction; }
    public void setReaction(String reaction) { this.reaction = reaction; }
    public boolean isAdded() { return added; }
    public void setAdded(boolean added) { this.added = added; }
}

