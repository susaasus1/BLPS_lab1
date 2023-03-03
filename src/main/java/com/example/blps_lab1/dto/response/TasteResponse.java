package com.example.blps_lab1.dto.response;

import lombok.Data;

@Data
public class TasteResponse {
    private Long tasteId;
    private String taste;

    public TasteResponse(Long tasteId, String taste) {
        this.tasteId = tasteId;
        this.taste = taste;
    }
}
