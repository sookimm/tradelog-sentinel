package com.sooyeon.tradelogsentinel.dto;

public class CreateLogRequest {

    private String level;
    private String message;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}