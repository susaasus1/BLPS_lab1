package com.example.blps_lab1.dto.response;

public class DishResponse {
    private Long dishId;
    private String name;
    private String description;

    public DishResponse(Long dishId, String name, String description) {
        this.dishId = dishId;
        this.name = name;
        this.description = description;
    }
}
