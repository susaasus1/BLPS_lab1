package com.example.blps_lab1.dto;

public class GetRecipeRequest {
    private Long recipe_id;

    public GetRecipeRequest(Long recipe_id) {
        this.recipe_id = recipe_id;
    }

    public Long getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(Long recipe_id) {
        this.recipe_id = recipe_id;
    }
}
