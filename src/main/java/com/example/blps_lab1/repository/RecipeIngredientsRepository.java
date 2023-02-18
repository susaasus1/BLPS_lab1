package com.example.blps_lab1.repository;

import com.example.blps_lab1.model.Recipe;
import com.example.blps_lab1.model.RecipeIngredients;
import com.example.blps_lab1.model.RecipeIngredientsPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientsRepository extends JpaRepository<RecipeIngredients, RecipeIngredientsPK> {
    List<RecipeIngredients> findAllByRecipe(Recipe recipe);
}
