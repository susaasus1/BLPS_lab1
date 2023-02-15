package com.example.blps_lab1.dto;

public class SuccessResponse {
    public String message;

    public SuccessResponse(String message) {
        this.message = message;
    }

    public SuccessResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
