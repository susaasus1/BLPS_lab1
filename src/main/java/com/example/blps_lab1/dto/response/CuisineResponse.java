package com.example.blps_lab1.dto.response;

import lombok.Data;

@Data
public class CuisineResponse {
    private Long cuisineId;
    private String cuisine;

    public CuisineResponse(Long cuisineId, String cuisine) {
        this.cuisineId = cuisineId;
        this.cuisine = cuisine;
    }
}
