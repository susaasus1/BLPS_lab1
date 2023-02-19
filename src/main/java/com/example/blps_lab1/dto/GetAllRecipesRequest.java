package com.example.blps_lab1.dto;


import java.util.List;

public class GetAllRecipesRequest {
    private boolean desc;

    private boolean asc;

    private String nationalCuisine;

    private String dish;

    private List<String> tastes;

    private List<String> ingredients;


    public GetAllRecipesRequest(boolean desc, boolean asc, String nationalCuisine,
                                String dish, List<String> tastes, List<String> ingredients) {
        this.desc = desc;
        this.asc = asc;
        this.nationalCuisine = nationalCuisine;
        this.dish = dish;
        this.tastes = tastes;
        this.ingredients = ingredients;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public String getNationalCuisine() {
        return nationalCuisine;
    }

    public void setNationalCuisine(String nationalCuisine) {
        this.nationalCuisine = nationalCuisine;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public List<String> getTastes() {
        return tastes;
    }

    public void setTastes(List<String> tastes) {
        this.tastes = tastes;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
