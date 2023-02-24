package com.example.blps_lab1.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class GetAllRecipesRequest {

    private boolean desc;

    private String nationalCuisine;

    private String dish;

    private List<String> tastes;

    private List<String> ingredients;


    public GetAllRecipesRequest(boolean desc, String nationalCuisine,
                                String dish, List<String> tastes, List<String> ingredients) {
        this.desc = desc;
        this.nationalCuisine = nationalCuisine;
        this.dish = dish;
        this.tastes = tastes;
        this.ingredients = ingredients;
    }

}
