package com.example.blps_lab1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteRecipeRequest {
    @NotNull(message = "Укажите номер рецепта!")
    private Long recipe_id;

    public DeleteRecipeRequest(Long recipe_id) {
        this.recipe_id = recipe_id;
    }

}
