package com.example.blps_lab1.dto;

import com.example.blps_lab1.model.*;
import jakarta.persistence.*;

import java.util.List;

public class RecipeResponse {

    private Long recipe_number;
    private String description;
    private Integer countPortion;

    private String user_login;


    private NationalCuisine nationalCuisine;


    private Dish dish;


    private List<Tastes> tastes;


    private List<Ingredients> ingredients;


    public RecipeResponse(Long recipe_number, String description, Integer countPortion,
                          String user_login, NationalCuisine nationalCuisine, Dish dish, List<Tastes> tastes, List<Ingredients> ingredients) {
        this.recipe_number = recipe_number;
        this.description = description;
        this.countPortion = countPortion;
        this.user_login = user_login;
        this.nationalCuisine = nationalCuisine;
        this.dish = dish;
        this.tastes = tastes;
        this.ingredients = ingredients;
    }

    public Long getRecipe_number() {
        return recipe_number;
    }

    public void setRecipe_number(Long recipe_number) {
        this.recipe_number = recipe_number;
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

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public NationalCuisine getNationalCuisine() {
        return nationalCuisine;
    }

    public void setNationalCuisine(NationalCuisine nationalCuisine) {
        this.nationalCuisine = nationalCuisine;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public List<Tastes> getTastes() {
        return tastes;
    }

    public void setTastes(List<Tastes> tastes) {
        this.tastes = tastes;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }
}
