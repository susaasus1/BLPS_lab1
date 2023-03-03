package com.example.blps_lab1.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateIngredientRequest {
    private String description;
    private String name;

    public UpdateIngredientRequest(String description, String name) {
        this.description = description;
        this.name = name;
    }
}
