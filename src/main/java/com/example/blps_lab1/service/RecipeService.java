package com.example.blps_lab1.service;

import com.example.blps_lab1.config.RecipeMapper;
import com.example.blps_lab1.dto.AddRecipeRequest;
import com.example.blps_lab1.dto.DeleteRecipeRequest;
import com.example.blps_lab1.dto.UpdateRecipeRequest;
import com.example.blps_lab1.exception.*;
import com.example.blps_lab1.model.*;
import com.example.blps_lab1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final DishRepository dishRepository;

    private final UserRepository userRepository;

    private final NationalCuisineRepository nationalCuisineRepository;

    private final TastesRepository tastesRepository;

    private final RecipeMapper recipeMapper;

    public RecipeService(RecipeRepository recipeRepository, DishRepository dishRepository, UserRepository userRepository, NationalCuisineRepository nationalCuisineRepository, TastesRepository tastesRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
        this.nationalCuisineRepository = nationalCuisineRepository;
        this.tastesRepository = tastesRepository;
        this.recipeMapper = recipeMapper;
    }


    public Recipe saveRecipe(String login, AddRecipeRequest addRecipeRequest) throws DishNotFoundException,
            UsernameNotFoundException, CuisineNotFoundException, TasteNotFoundException {
        Dish dish = dishRepository.findByName(addRecipeRequest.getDish_name()).orElseThrow(() -> new DishNotFoundException("Dish " + addRecipeRequest.getDish_name() + " not found!"));
        User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User with login " + login + " doesn't exist!"));
        NationalCuisine nationalCuisine = nationalCuisineRepository.
                findByCuisine(addRecipeRequest.getNationalCuisine_name()).
                orElseThrow(() -> new CuisineNotFoundException("National cuisine " + addRecipeRequest.getNationalCuisine_name() + " doesn't exist!"));
        List<Tastes> tastesList = new ArrayList<>();
        for (String taste_name : addRecipeRequest.getTastes_names()) {
            Tastes taste = tastesRepository.findByTaste(taste_name).orElseThrow(() ->
                    new TasteNotFoundException("Taste " + taste_name + " doesn't exist!"));
            tastesList.add(taste);
        }
        Recipe recipe = new Recipe(addRecipeRequest.getDescription(), addRecipeRequest.getCountPortion(), user, nationalCuisine, dish, tastesList);
        recipeRepository.save(recipe);
        return recipe;
    }

    public void deleteRecipe(String login, DeleteRecipeRequest deleteRecipeRequest) throws RecipeNotFoundException, UsernameNotFoundException, NotOwnerException {
        Recipe recipe = recipeRepository.findById(deleteRecipeRequest.getRecipe_id()).orElseThrow(() -> new RecipeNotFoundException("Рецепт с номером " + deleteRecipeRequest.getRecipe_id() + " не найден в базе!"));
        User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User with login " + login + " doesn't exist!"));
        if (user.getLogin().equals(recipe.getUser().getLogin())) recipeRepository.delete(recipe);
        else
            throw new NotOwnerException("Вы не являетесь владельцем рецепта с номером " + deleteRecipeRequest.getRecipe_id());
    }

    public void updateRecipe(String login, UpdateRecipeRequest updateRecipeRequest) throws RecipeNotFoundException, UsernameNotFoundException, NotOwnerException {
        Recipe recipe = recipeRepository.findById(updateRecipeRequest.getRecipe_id()).orElseThrow(() -> new RecipeNotFoundException("Рецепт с номером " + updateRecipeRequest.getRecipe_id() + " не найден в базе!"));
        User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User with login " + login + " doesn't exist!"));

        if (user.getLogin().equals(recipe.getUser().getLogin())) {
            recipeMapper.updateRecipeFromDto(updateRecipeRequest, recipe);
            recipeRepository.save(recipe);
        } else
            throw new NotOwnerException("Вы не являетесь владельцем рецепта с номером " + updateRecipeRequest.getRecipe_id());

    }


}
