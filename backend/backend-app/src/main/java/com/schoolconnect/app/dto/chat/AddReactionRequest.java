package com.schoolconnect.app.dto.chat;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddReactionRequest {
    private String reaction;
    
    public AddReactionRequest() {}
    
    public String getReaction() {
        return reaction;
    }
    
    public void setReaction(String reaction) {
        this.reaction = reaction;
    }
}
