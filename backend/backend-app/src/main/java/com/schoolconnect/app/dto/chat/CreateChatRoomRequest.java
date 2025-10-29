package com.schoolconnect.app.dto.chat;

import java.util.List;

public class CreateChatRoomRequest {
    private String name;
    private List<Long> participantIds;
    
    public CreateChatRoomRequest() {}
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Long> getParticipantIds() { return participantIds; }
    public void setParticipantIds(List<Long> participantIds) { this.participantIds = participantIds; }
}
