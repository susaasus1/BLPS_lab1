package com.example.blps_lab1.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe_ingredients")
@Data
@NoArgsConstructor
public class RecipeIngredients {

    @EmbeddedId
    private RecipeIngredientsPK id;

    private Double gram;


}
