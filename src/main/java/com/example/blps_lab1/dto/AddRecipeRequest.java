package com.example.blps_lab1.dto;

import java.util.List;

public class AddRecipeRequest {
    private String dish_name;

    private String description;
    private Integer countPortion;

    private String nationalCuisine_name;

    private List<String> tastes_names;

    public AddRecipeRequest(String dish_name, String description, Integer countPortion, String nationalCuisine_name, List<String> tastes_names) {
        this.dish_name = dish_name;
        this.description = description;
        this.countPortion = countPortion;
        this.nationalCuisine_name = nationalCuisine_name;
        this.tastes_names = tastes_names;
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
}
