package com.example.blps_lab1.service;

import com.example.blps_lab1.exception.IngredientNotFoundException;
import com.example.blps_lab1.model.Ingredients;
import com.example.blps_lab1.repository.IngredientsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientsService {

    private final IngredientsRepository ingredientsRepository;

    public IngredientsService(IngredientsRepository ingredientsRepository) {
        this.ingredientsRepository = ingredientsRepository;
    }

    public Ingredients findIngredientByName(String name) throws IngredientNotFoundException {
        return ingredientsRepository.findIngredientsByName(name).orElseThrow(() -> new IngredientNotFoundException("Ингредиент " + name + " не найден"));
    }


    public List<Ingredients> findAllIngredientsByNames(List<String> names) throws IngredientNotFoundException {
        List<Ingredients> ingredientsList = new ArrayList<>();
        for (String ingredient_name : names) {
            Ingredients ingredient = findIngredientByName(ingredient_name);
            ingredientsList.add(ingredient);
        }
        return ingredientsList;
    }
}
