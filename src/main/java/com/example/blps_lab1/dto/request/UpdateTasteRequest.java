package com.example.blps_lab1.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateTasteRequest {
    private String taste;

    public UpdateTasteRequest(String taste) {
        this.taste = taste;
    }
}
