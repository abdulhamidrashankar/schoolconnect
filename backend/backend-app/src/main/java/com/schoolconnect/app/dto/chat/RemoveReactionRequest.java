package com.schoolconnect.app.dto.chat;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoveReactionRequest {
    private String reaction;
    
    public RemoveReactionRequest() {}
    
    public String getReaction() {
        return reaction;
    }
    
    public void setReaction(String reaction) {
        this.reaction = reaction;
    }
}
