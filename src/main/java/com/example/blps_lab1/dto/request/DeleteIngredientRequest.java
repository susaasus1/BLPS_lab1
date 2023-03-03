package com.example.blps_lab1.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteIngredientRequest {
    @NotNull(message = "Укажите номер ингредиента!")
    private Long ingredientId;

    public DeleteIngredientRequest(Long ingredientId) {
        this.ingredientId = ingredientId;
    }
}
