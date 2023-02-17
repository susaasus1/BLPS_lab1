package com.example.blps_lab1.service;

import com.example.blps_lab1.exception.CuisineNotFoundException;
import com.example.blps_lab1.exception.DishNotFoundException;
import com.example.blps_lab1.exception.TasteNotFoundException;
import com.example.blps_lab1.model.*;
import com.example.blps_lab1.repository.*;
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


    public RecipeService(RecipeRepository recipeRepository, DishRepository dishRepository, UserRepository userRepository, NationalCuisineRepository nationalCuisineRepository, TastesRepository tastesRepository) {
        this.recipeRepository = recipeRepository;
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
        this.nationalCuisineRepository = nationalCuisineRepository;
        this.tastesRepository = tastesRepository;
    }


    public Recipe saveRecipe(String login, String dish_name, String description,
                             Integer countPortion, String nationalCuisine_name,
                             List<String> tastes_names) throws DishNotFoundException,
            UsernameNotFoundException, CuisineNotFoundException, TasteNotFoundException {
        Dish dish = dishRepository.findByName(dish_name).orElseThrow(() -> new DishNotFoundException("Dish " + dish_name + " not found!"));
        User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User with login " + login + " doesn't exist!"));
        NationalCuisine nationalCuisine = nationalCuisineRepository.
                findByCuisine(nationalCuisine_name).
                orElseThrow(() -> new CuisineNotFoundException("National cuisine " + nationalCuisine_name + " doesn't exist!"));
        List<Tastes> tastesList = new ArrayList<>();
        for (String taste_name : tastes_names) {
            Tastes taste = tastesRepository.findByTaste(taste_name).orElseThrow(() ->
                    new TasteNotFoundException("Taste " + taste_name + " doesn't exist!"));
            tastesList.add(taste);
        }
        Recipe recipe = new Recipe(description, countPortion, user, nationalCuisine, dish, tastesList);
        recipeRepository.save(recipe);
        return recipe;
    }
}
