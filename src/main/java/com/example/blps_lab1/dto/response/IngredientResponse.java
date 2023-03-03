package com.example.blps_lab1.dto.response;

public class IngredientResponse {
    private Long ingredientId;
    private String description;
    private String name;

    public IngredientResponse(Long ingredientId, String description, String name) {
        this.ingredientId = ingredientId;
        this.description = description;
        this.name = name;
    }
}
