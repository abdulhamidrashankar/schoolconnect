package com.schoolconnect.app.dto.chat;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String username;
    private String role;
    private String displayName;
    private boolean online;
    
    public UserDTO() {}
    
    public UserDTO(Long id, String username, String role, String displayName) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.displayName = displayName;
        this.online = false;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    
    public boolean isOnline() { return online; }
    public void setOnline(boolean online) { this.online = online; }
}
