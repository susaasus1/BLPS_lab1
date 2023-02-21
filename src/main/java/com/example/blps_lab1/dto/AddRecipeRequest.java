package com.example.blps_lab1.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AddRecipeRequest {
    @NotBlank(message = "Укажите название блюда!")
    @Size(min = 1, max = 255, message = "Укажите название блюда! От 1 до 255 символов")
    private String dish_name;

    @NotBlank(message = "Укажите описание рецепта!")
    @Size(min = 1, max = 4096, message = "Укажите описание рецепта! От 1 до 4096 символов")
    private String description;
    @NotNull(message = "Укажите количество порций!")
    @Min(value = 1, message = "Количество порций не может быть меньше 1!")
    @Max(value = 100, message = "Предел порций - 100")
    private Integer countPortion;

    @NotBlank(message = "Укажите национальную кухню!")
    @Size(min = 1, max = 64, message = "Укажите национальную кухню! От 1 до 64 символов")
    private String nationalCuisine_name;

    @NotNull(message = "Укажите вкусы!")
    private List<String> tastes_names;

    @NotNull(message = "Укажите ингредиенты!")
    private List<String> ingredients_names;


    public AddRecipeRequest(String dish_name, String description, Integer countPortion,
                            String nationalCuisine_name, List<String> tastes_names, List<String> ingredients_names) {
        this.dish_name = dish_name;
        this.description = description;
        this.countPortion = countPortion;
        this.nationalCuisine_name = nationalCuisine_name;
        this.tastes_names = tastes_names;
        this.ingredients_names = ingredients_names;
    }

}
