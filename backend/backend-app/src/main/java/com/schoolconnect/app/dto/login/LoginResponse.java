package com.schoolconnect.app.dto.login;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private String message;
    private String token; // or sessionId or null
    
	public LoginResponse(String message) {
		this.message = message;
	}
	
	public LoginResponse(String message, String token) {
		this.message = message;
		this.token = token;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
 
