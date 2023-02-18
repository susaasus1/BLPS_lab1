package com.example.blps_lab1.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class RecipeIngredientsPK implements Serializable {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient_id")
    private Ingredients ingredient;

    public RecipeIngredientsPK(Recipe recipe, Ingredients ingredient) {
        this.recipe = recipe;
        this.ingredient = ingredient;
    }
}
