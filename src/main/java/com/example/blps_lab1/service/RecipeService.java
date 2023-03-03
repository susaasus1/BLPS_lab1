package com.example.blps_lab1.service;

import com.example.blps_lab1.dto.request.AddRecipeRequest;
import com.example.blps_lab1.dto.request.DeleteRecipeRequest;
import com.example.blps_lab1.dto.request.GetAllRecipesRequest;
import com.example.blps_lab1.dto.request.UpdateRecipeRequest;
import com.example.blps_lab1.exception.*;
import com.example.blps_lab1.model.*;
import com.example.blps_lab1.repository.RecipeRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    private final UserService userService;

    private final DishService dishService;

    private final IngredientsService ingredientsService;

    private final TastesService tastesService;

    private final NationalCuisineService nationalCuisineService;


    public RecipeService(RecipeRepository recipeRepository, UserService userService, DishService dishService,
                         IngredientsService ingredientsService, TastesService tastesService,
                         NationalCuisineService nationalCuisineService) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
        this.dishService = dishService;
        this.ingredientsService = ingredientsService;
        this.tastesService = tastesService;
        this.nationalCuisineService = nationalCuisineService;
    }


    public Recipe saveRecipe(String login, AddRecipeRequest addRecipeRequest) throws DishNotFoundException,
            UsernameNotFoundException, CuisineNotFoundException, TasteNotFoundException, IngredientNotFoundException {
        Dish dish = dishService.findDishByName(addRecipeRequest.getDishName());
        User user = userService.findUserByLogin(login);
        NationalCuisine nationalCuisine = nationalCuisineService.findNationalCuisineByName(addRecipeRequest.getNationalCuisineName());
        List<Tastes> tastesList = tastesService.findAllTastesByTasteNames(addRecipeRequest.getTastesNames());
        List<Ingredients> ingredientsList = ingredientsService.findAllIngredientsByNames(addRecipeRequest.getIngredientsNames());
        Recipe recipe = new Recipe(addRecipeRequest.getDescription(),
                addRecipeRequest.getCountPortion(), user, nationalCuisine, dish, tastesList, ingredientsList);
        recipeRepository.save(recipe);
        return recipe;
    }

    public Recipe findRecipeById(Long id) throws RecipeNotFoundException {
        return recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException("Рецепт с номером " + id + " не найден в базе!"));
    }

    public void checkUserOnRecipeOwner(User user, Recipe recipe) throws NotOwnerException {
        if (!user.getLogin().equals(recipe.getUser().getLogin()))
            throw new NotOwnerException("Пользователь " + user.getLogin() +
                    " не является владельцем рецепта по номеру " + recipe.getId());
    }

    public void deleteRecipe(String login, DeleteRecipeRequest deleteRecipeRequest) throws RecipeNotFoundException, UsernameNotFoundException, NotOwnerException {
        Recipe recipe = findRecipeById(deleteRecipeRequest.getRecipeId());
        User user = userService.findUserByLogin(login);
        checkUserOnRecipeOwner(user, recipe);

        recipeRepository.delete(recipe);
    }

    public void updateRecipe(String login, UpdateRecipeRequest updateRecipeRequest) throws
            DishNotFoundException, RecipeNotFoundException, UsernameNotFoundException,
            NotOwnerException, CuisineNotFoundException, TasteNotFoundException, IngredientNotFoundException {
        Recipe recipe = findRecipeById(updateRecipeRequest.getRecipeId());
        User user = userService.findUserByLogin(login);

        checkUserOnRecipeOwner(user, recipe);


        if (updateRecipeRequest.getDishName() != null) {
            Dish dish = dishService.findDishByName(updateRecipeRequest.getDishName());
            recipe.setDish(dish);
        }

        if (updateRecipeRequest.getDescription() != null) {
            recipe.setDescription(updateRecipeRequest.getDescription());
        }

        if (updateRecipeRequest.getCountPortion() != null) {
            recipe.setCountPortion(updateRecipeRequest.getCountPortion());
        }

        if (updateRecipeRequest.getNationalCuisineName() != null) {
            NationalCuisine nationalCuisine = nationalCuisineService.
                    findNationalCuisineByName(updateRecipeRequest.getNationalCuisineName());
            recipe.setNationalCuisine(nationalCuisine);
        }

        if (updateRecipeRequest.getTastesNames() != null) {
            List<Tastes> tastesList = tastesService.
                    findAllTastesByTasteNames(updateRecipeRequest.getTastesNames());
            recipe.setTastes(tastesList);
        }

        if (updateRecipeRequest.getIngredientsNames() != null) {
            List<Ingredients> ingredientsList = ingredientsService.
                    findAllIngredientsByNames(updateRecipeRequest.getIngredientsNames());
            recipe.setIngredients(ingredientsList);
        }


        recipeRepository.save(recipe);
    }

    public List<Recipe> getAllRecipes(GetAllRecipesRequest getAllRecipesRequest)
            throws CuisineNotFoundException, DishNotFoundException {
        List<Recipe> recipeList;
        if (getAllRecipesRequest.getNationalCuisine() != null && getAllRecipesRequest.getDish() != null) {
            NationalCuisine nationalCuisine = nationalCuisineService.findNationalCuisineByName(getAllRecipesRequest.getNationalCuisine());
            Dish dish = dishService.findDishByName(getAllRecipesRequest.getDish());
            recipeList = recipeRepository.findAllByNationalCuisineAndDish(nationalCuisine, dish);
        } else if (getAllRecipesRequest.getNationalCuisine() != null) {
            NationalCuisine nationalCuisine = nationalCuisineService.findNationalCuisineByName(getAllRecipesRequest.getNationalCuisine());
            recipeList = recipeRepository.findAllByNationalCuisine(nationalCuisine);
        } else if (getAllRecipesRequest.getDish() != null) {
            Dish dish = dishService.findDishByName(getAllRecipesRequest.getDish());
            recipeList = recipeRepository.findAllByDish(dish);
        } else {
            recipeList = recipeRepository.findAll();
        }

        List<Recipe> recipeListWithTastes = new ArrayList<>();
        if (getAllRecipesRequest.getTastes() != null) {
            recipeList.forEach(recipe -> {
                List<String> tastes = recipe.getTastes().stream()
                        .map(Tastes::getTaste).toList();
                if (new HashSet<>(tastes).containsAll(getAllRecipesRequest.getTastes())) {
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
                if (new HashSet<>(ingredients).containsAll(getAllRecipesRequest.getIngredients())) {
                    recipeListWithIngredients.add(recipe);
                }
            });
            recipeList = recipeListWithIngredients;
        }


        recipeList.sort(Comparator.comparing(Recipe::getId));
        if (getAllRecipesRequest.isDesc()) Collections.reverse(recipeList);

        return recipeList;
    }



}
