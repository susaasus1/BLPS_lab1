package com.example.blps_lab1.service;

import com.example.blps_lab1.dto.AddRecipeRequest;
import com.example.blps_lab1.dto.DeleteRecipeRequest;
import com.example.blps_lab1.dto.GetAllRecipesRequest;
import com.example.blps_lab1.dto.UpdateRecipeRequest;
import com.example.blps_lab1.exception.*;
import com.example.blps_lab1.model.*;
import com.example.blps_lab1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final DishRepository dishRepository;

    private final UserRepository userRepository;

    private final NationalCuisineRepository nationalCuisineRepository;

    private final TastesRepository tastesRepository;

    private final IngredientsRepository ingredientsRepository;


    public RecipeService(RecipeRepository recipeRepository, DishRepository dishRepository, UserRepository userRepository, NationalCuisineRepository nationalCuisineRepository, TastesRepository tastesRepository, IngredientsRepository ingredientsRepository) {
        this.recipeRepository = recipeRepository;
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
        this.nationalCuisineRepository = nationalCuisineRepository;
        this.tastesRepository = tastesRepository;
        this.ingredientsRepository = ingredientsRepository;
    }


    public Recipe saveRecipe(String login, AddRecipeRequest addRecipeRequest) throws DishNotFoundException,
            UsernameNotFoundException, CuisineNotFoundException, TasteNotFoundException, IngredientNotFoundException {
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
        List<Ingredients> ingredientsList = new ArrayList<>();
        for (String ingredient_name : addRecipeRequest.getIngredients_names()) {
            Ingredients ingredient = ingredientsRepository.findByIngredient(ingredient_name).orElseThrow(() ->
                    new IngredientNotFoundException("Ingredient " + ingredient_name + " doesn't exist!"));
            ingredientsList.add(ingredient);
        }
        Recipe recipe = new Recipe(addRecipeRequest.getDescription(), addRecipeRequest.getCountPortion(), user, nationalCuisine, dish, tastesList, ingredientsList);
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

    public void updateRecipe(String login, UpdateRecipeRequest updateRecipeRequest) throws
            DishNotFoundException, RecipeNotFoundException, UsernameNotFoundException,
            NotOwnerException, CuisineNotFoundException, TasteNotFoundException, IngredientNotFoundException {
        Recipe recipe = recipeRepository.findById(updateRecipeRequest.getRecipe_id()).orElseThrow(() -> new RecipeNotFoundException("Рецепт с номером " + updateRecipeRequest.getRecipe_id() + " не найден в базе!"));
        User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User with login " + login + " doesn't exist!"));

        if (!user.getLogin().equals(recipe.getUser().getLogin()))
            throw new NotOwnerException("Вы не являетесь владельцем рецепта с номером " + updateRecipeRequest.getRecipe_id());

        if (updateRecipeRequest.getDish_name() != null) {
            Dish dish = dishRepository.findByName(updateRecipeRequest.getDish_name()).orElseThrow(() -> new DishNotFoundException("Dish " + updateRecipeRequest.getDish_name() + " not found!"));
            recipe.setDish(dish);
        }

        if (updateRecipeRequest.getDescription() != null) {
            recipe.setDescription(updateRecipeRequest.getDescription());
        }

        if (updateRecipeRequest.getCountPortion() != null) {
            recipe.setCountPortion(updateRecipeRequest.getCountPortion());
        }

        if (updateRecipeRequest.getNationalCuisine_name() != null) {
            NationalCuisine nationalCuisine = nationalCuisineRepository.
                    findByCuisine(updateRecipeRequest.getNationalCuisine_name()).
                    orElseThrow(() -> new CuisineNotFoundException("National cuisine " + updateRecipeRequest.getNationalCuisine_name() + " doesn't exist!"));
            recipe.setNationalCuisine(nationalCuisine);
        }

        if (updateRecipeRequest.getTastes_names() != null) {
            List<Tastes> tastesList = new ArrayList<>();
            for (String taste_name : updateRecipeRequest.getTastes_names()) {
                Tastes taste = tastesRepository.findByTaste(taste_name).orElseThrow(() ->
                        new TasteNotFoundException("Taste " + taste_name + " doesn't exist!"));
                tastesList.add(taste);
            }
            recipe.setTastes(tastesList);
        }

        if (updateRecipeRequest.getIngredients_names() != null) {
            List<Ingredients> ingredientsList = new ArrayList<>();
            for (String ingredient_name : updateRecipeRequest.getIngredients_names()) {
                Ingredients ingredient = ingredientsRepository.findByIngredient(ingredient_name).orElseThrow(() ->
                        new IngredientNotFoundException("Ingredient " + ingredient_name + " doesn't exist!"));
                ingredientsList.add(ingredient);
            }
            recipe.setIngredients(ingredientsList);
        }


        recipeRepository.save(recipe);
    }

    public List<Recipe> getAllRecipes(GetAllRecipesRequest getAllRecipesRequest)
            throws AscDescException, CuisineNotFoundException, DishNotFoundException {
        if (getAllRecipesRequest.isDesc() && getAllRecipesRequest.isAsc())
            throw new AscDescException("Вы не можете сортировать сразу по возрастанию и убыванию, выберите один критерий!");
        List<Recipe> recipeList;
        if (getAllRecipesRequest.getNationalCuisine() != null && getAllRecipesRequest.getDish() != null) {
            NationalCuisine nationalCuisine = nationalCuisineRepository.
                    findByCuisine(getAllRecipesRequest.getNationalCuisine()).
                    orElseThrow(() -> new CuisineNotFoundException("National cuisine " + getAllRecipesRequest.getNationalCuisine() + " doesn't exist!"));
            Dish dish = dishRepository.findByName(getAllRecipesRequest.getDish()).orElseThrow(() -> new DishNotFoundException("Dish " + getAllRecipesRequest.getDish() + " not found!"));
            recipeList = recipeRepository.findAllByNationalCuisineAndDish(nationalCuisine, dish);
        } else if (getAllRecipesRequest.getNationalCuisine() != null) {
            NationalCuisine nationalCuisine = nationalCuisineRepository.
                    findByCuisine(getAllRecipesRequest.getNationalCuisine()).
                    orElseThrow(() -> new CuisineNotFoundException("National cuisine " + getAllRecipesRequest.getNationalCuisine() + " doesn't exist!"));
            recipeList = recipeRepository.findAllByNationalCuisine(nationalCuisine);
        } else if (getAllRecipesRequest.getDish() != null) {
            Dish dish = dishRepository.findByName(getAllRecipesRequest.getDish()).orElseThrow(() -> new DishNotFoundException("Dish " + getAllRecipesRequest.getDish() + " not found!"));
            recipeList = recipeRepository.findAllByDish(dish);
        } else {
            recipeList = recipeRepository.findAll();
        }

        List<Recipe> recipeListWithTastes = new ArrayList<>();
        if (getAllRecipesRequest.getTastes() != null) {
            recipeList.forEach(recipe -> {
                List<String> tastes = recipe.getTastes().stream()
                        .map(Tastes::getTaste).toList();
                if (tastes.containsAll(getAllRecipesRequest.getTastes())) {
                    recipeListWithTastes.add(recipe);
                }
            });
            recipeList = recipeListWithTastes;
        }

        List<Recipe> recipeListWithIngredients = new ArrayList<>();
        if (getAllRecipesRequest.getIngredients() != null) {
            recipeList.forEach(recipe -> {
                List<String> ingredients = recipe.getIngredients().stream()
                        .map(Ingredients::getName).toList();
                if (ingredients.containsAll(getAllRecipesRequest.getIngredients())) {
                    recipeListWithIngredients.add(recipe);
                }
            });
            recipeList = recipeListWithIngredients;
        }


        recipeList.sort(Comparator.comparing(Recipe::getId));
        if (getAllRecipesRequest.isDesc()) Collections.reverse(recipeList);

        return recipeList;
    }

    public Recipe getRecipeById(Long recipe_id) throws RecipeNotFoundException {
        return recipeRepository.findById(recipe_id).orElseThrow(() -> new RecipeNotFoundException("Рецепт с номером " + recipe_id + " не найден в базе!"));
    }


}
