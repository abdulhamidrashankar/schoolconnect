package com.schoolconnect.app.dto.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateAnnouncementRequest {
    private String name;
    private List<Long> participantIds;
    
    public CreateAnnouncementRequest() {}
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Long> getParticipantIds() {
        return participantIds;
    }
    
    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }
}
