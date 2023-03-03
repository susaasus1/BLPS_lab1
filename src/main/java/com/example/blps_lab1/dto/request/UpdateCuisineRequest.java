package com.example.blps_lab1.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateCuisineRequest {
    private String cuisine;

    public UpdateCuisineRequest(String cuisine) {
        this.cuisine = cuisine;
    }
}
