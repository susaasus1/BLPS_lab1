package com.example.blps_lab1.dto;

import jakarta.validation.constraints.*;

import java.util.List;

public class AddRecipeRequest {
    @NotBlank(message = "Укажите название блюда!")
    @Size(min = 1, max = 255, message = "Укажите название блюда! От 1 до 255 символов")
    private String dish_name;

    @NotBlank(message = "Укажите описание рецепта!")
    @Size(min = 1, max = 512, message = "Укажите описание рецепта! От 1 до 512 символов")
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

    public AddRecipeRequest() {
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
