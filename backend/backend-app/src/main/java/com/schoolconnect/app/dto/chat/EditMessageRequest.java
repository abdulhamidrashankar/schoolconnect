package com.schoolconnect.app.dto.chat;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EditMessageRequest {
    private String newBody;
    
    public EditMessageRequest() {}
    
    public String getNewBody() {
        return newBody;
    }
    
    public void setNewBody(String newBody) {
        this.newBody = newBody;
    }
}
