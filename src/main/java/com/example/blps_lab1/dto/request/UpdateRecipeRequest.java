package com.example.blps_lab1.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class UpdateRecipeRequest {
    @NotNull(message = "Укажите номер рецепта!")
    private Long recipeId;
    private String dishName;

    private String description;
    private Integer countPortion;

    private String nationalCuisineName;

    private List<String> tastesNames;

    private List<String> ingredientsNames;


    public UpdateRecipeRequest(Long recipeId, String dishName, String description,
                               Integer countPortion,
                               String nationalCuisineName, List<String> tastesNames,
                               List<String> ingredientsNames) {
        this.recipeId = recipeId;
        this.dishName = dishName;
        this.description = description;
        this.countPortion = countPortion;
        this.nationalCuisineName = nationalCuisineName;
        this.tastesNames = tastesNames;
        this.ingredientsNames = ingredientsNames;
    }
}
