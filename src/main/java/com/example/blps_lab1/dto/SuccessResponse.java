package com.example.blps_lab1.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SuccessResponse {
    public String message;

    public SuccessResponse(String message) {
        this.message = message;
    }

}
