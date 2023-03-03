package com.example.blps_lab1.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateDishRequest {
    private String name;
    private String description;

    public UpdateDishRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
