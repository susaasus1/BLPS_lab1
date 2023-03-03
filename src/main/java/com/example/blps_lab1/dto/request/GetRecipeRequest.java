package com.example.blps_lab1.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetRecipeRequest {
    @NotNull(message = "Укажите номер рецепта!")
    private Long recipeId;

    public GetRecipeRequest(Long recipeId) {
        this.recipeId = recipeId;
    }
}
