package com.example.blps_lab1.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class UpdateRecipeRequest {
    @NotNull
    private Long recipe_id;
    private String dish_name;

    private String description;
    private Integer countPortion;

    private String nationalCuisine_name;

    private List<String> tastes_names;

    private List<String> ingredients_names;


    public UpdateRecipeRequest(Long recipe_id, String dish_name, String description,
                               Integer countPortion,
                               String nationalCuisine_name, List<String> tastes_names,
                               List<String> ingredients_names) {
        this.recipe_id = recipe_id;
        this.dish_name = dish_name;
        this.description = description;
        this.countPortion = countPortion;
        this.nationalCuisine_name = nationalCuisine_name;
        this.tastes_names = tastes_names;
        this.ingredients_names = ingredients_names;
    }

    public Long getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(Long recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCountPortion() {
        return countPortion;
    }

    public void setCountPortion(Integer countPortion) {
        this.countPortion = countPortion;
    }

    public String getNationalCuisine_name() {
        return nationalCuisine_name;
    }

    public void setNationalCuisine_name(String nationalCuisine_name) {
        this.nationalCuisine_name = nationalCuisine_name;
    }

    public List<String> getTastes_names() {
        return tastes_names;
    }

    public void setTastes_names(List<String> tastes_names) {
        this.tastes_names = tastes_names;
    }

    public List<String> getIngredients_names() {
        return ingredients_names;
    }

    public void setIngredients_names(List<String> ingredients_names) {
        this.ingredients_names = ingredients_names;
    }
}
