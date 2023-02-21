package com.example.blps_lab1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class UpdateRecipeRequest {
    @NotNull(message = "Укажите номер рецепта!")
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
}
