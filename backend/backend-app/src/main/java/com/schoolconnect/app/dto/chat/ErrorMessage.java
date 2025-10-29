package com.schoolconnect.app.dto.chat;


public class ErrorMessage {
    private String message;
    private long timestamp;

    public ErrorMessage() {
        this.timestamp = System.currentTimeMillis();
    }

    public ErrorMessage(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}