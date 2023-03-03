package com.example.blps_lab1.service;


import com.example.blps_lab1.dto.request.AddIngredientRequest;
import com.example.blps_lab1.dto.request.UpdateIngredientRequest;
import com.example.blps_lab1.exception.IllegalPageParametersException;
import com.example.blps_lab1.exception.IngredientAlreadyExistException;
import com.example.blps_lab1.exception.IngredientNotFoundException;
import com.example.blps_lab1.exception.ResourceNotFoundException;
import com.example.blps_lab1.model.Ingredients;
import com.example.blps_lab1.repository.IngredientsRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public void saveIngredient(String name, String description) throws IngredientAlreadyExistException {
        Ingredients ingredient = new Ingredients(name, description);
        if (ingredientsRepository.existsIngredientsByName(name))
            throw new IngredientAlreadyExistException("Ингредиент " + name + " уже есть в базе данных!");
        ingredientsRepository.save(ingredient);
    }

    public void deleteIngredient(Long ingredientId) throws IngredientNotFoundException {
        if (!ingredientsRepository.existsById(ingredientId))
            throw new IngredientNotFoundException("Ингредиент с id=" + ingredientId + " не существует!");
        ingredientsRepository.deleteById(ingredientId);
    }

    public void updateIngredient(Long ingredientId, String name, String description) throws IngredientNotFoundException {
        Ingredients ingredient = ingredientsRepository.findIngredientsById(ingredientId).orElseThrow(() -> new IngredientNotFoundException("Ингредиент с id=" + ingredientId + " не существует!"));
        if (name != null) {
            ingredient.setName(name);
        }
        if (description != null) {
            ingredient.setDescription(description);
        }
        ingredientsRepository.save(ingredient);
    }

    public Ingredients getIngredient(Long ingredientId) throws IngredientNotFoundException {
        Ingredients ingredient = ingredientsRepository.findIngredientsById(ingredientId).orElseThrow(() -> new IngredientNotFoundException("Ингредиент с id=" + ingredientId + " не существует!"));
        return ingredient;
    }

    public Page<Ingredients> getIngredientsPage(int pageNum, int pageSize) throws IllegalPageParametersException, ResourceNotFoundException {
        if (pageNum < 1 || pageSize < 1)
            throw new IllegalPageParametersException("Номер страницы и размер страницы должны быть больше 1!");
        Pageable pageRequest = createPageRequest(pageNum - 1, pageSize);
        Page<Ingredients> ingredients = ingredientsRepository.findAll(pageRequest);
        if (ingredients.getTotalPages() < pageNum)
            throw new ResourceNotFoundException("На указанной странице не найдено записей!");
        return ingredients;
    }

    private Pageable createPageRequest(int pageNum, int pageSize) {
        return PageRequest.of(pageNum, pageSize);
    }
}
