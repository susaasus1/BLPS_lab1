package com.example.blps_lab1.dto;

import com.example.blps_lab1.model.*;
import lombok.Data;

import java.util.List;

@Data
public class RecipeResponse {

    private Long recipeNumber;
    private String description;
    private Integer countPortion;
    private String userLogin;
    private NationalCuisine nationalCuisine;
    private Dish dish;
    private List<Tastes> tastes;
    private List<Ingredients> ingredients;

    public RecipeResponse(Long recipeNumber, String description, Integer countPortion,
                          String userLogin, NationalCuisine nationalCuisine, Dish dish, List<Tastes> tastes, List<Ingredients> ingredients) {
        this.recipeNumber = recipeNumber;
        this.description = description;
        this.countPortion = countPortion;
        this.userLogin = userLogin;
        this.nationalCuisine = nationalCuisine;
        this.dish = dish;
        this.tastes = tastes;
        this.ingredients = ingredients;
    }

}
