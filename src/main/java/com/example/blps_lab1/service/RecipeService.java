package com.example.blps_lab1.service;

import com.example.blps_lab1.exception.*;
import com.example.blps_lab1.model.*;
import com.example.blps_lab1.repository.RecipeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    public Recipe saveRecipe(String login, String dishName, String description,
                             Integer countPortion, String nationalCuisineName,
                             List<String> tastesNames, List<String> ingredientsNames) throws DishNotFoundException,
            UsernameNotFoundException, CuisineNotFoundException, TasteNotFoundException, IngredientNotFoundException {
        Dish dish = dishService.findDishByName(dishName);
        User user = userService.findUserByLogin(login);
        NationalCuisine nationalCuisine = nationalCuisineService.findNationalCuisineByName(nationalCuisineName);
        List<Tastes> tastesList = tastesService.findAllTastesByTasteNames(tastesNames);
        List<Ingredients> ingredientsList = ingredientsService.findAllIngredientsByNames(ingredientsNames);
        Recipe recipe = new Recipe(description,
                countPortion, user, nationalCuisine, dish, tastesList, ingredientsList);
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

    public void deleteRecipe(String login, Long id) throws RecipeNotFoundException, UsernameNotFoundException, NotOwnerException {
        Recipe recipe = findRecipeById(id);
        User user = userService.findUserByLogin(login);
        checkUserOnRecipeOwner(user, recipe);

        recipeRepository.delete(recipe);
    }

    public void updateRecipe(String login, Long id, String dishName, String description,
                             Integer countPortion, String nationalCuisineName,
                             List<String> tastesNames, List<String> ingredientsNames) throws
            DishNotFoundException, RecipeNotFoundException, UsernameNotFoundException,
            NotOwnerException, CuisineNotFoundException, TasteNotFoundException, IngredientNotFoundException {
        Recipe recipe = findRecipeById(id);
        User user = userService.findUserByLogin(login);

        checkUserOnRecipeOwner(user, recipe);

        Dish dish = dishService.findDishByName(dishName);
        NationalCuisine nationalCuisine = nationalCuisineService.
                findNationalCuisineByName(nationalCuisineName);
        List<Tastes> tastesList = tastesService.
                findAllTastesByTasteNames(tastesNames);
        List<Ingredients> ingredientsList = ingredientsService.
                findAllIngredientsByNames(ingredientsNames);

        recipe.setDish(dish);
        recipe.setNationalCuisine(nationalCuisine);
        recipe.setIngredients(ingredientsList);
        recipe.setTastes(tastesList);
        recipe.setDescription(description);
        recipe.setCountPortion(countPortion);

        recipeRepository.save(recipe);
    }

    public Page<Recipe> getAllRecipes(int page, int size, String nationalCuisine,
                                      String dish, List<String> sortList, String sortOrder) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        return recipeRepository.findByNationalCuisineLikeAndDishLike(nationalCuisine, dish, pageable);

    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }


}
