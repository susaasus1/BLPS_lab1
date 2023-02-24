package com.example.blps_lab1.controller;

import com.example.blps_lab1.config.jwt.AuthTokenFilter;
import com.example.blps_lab1.config.jwt.JwtUtils;
import com.example.blps_lab1.dto.*;
import com.example.blps_lab1.exception.*;
import com.example.blps_lab1.model.Recipe;
import com.example.blps_lab1.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/cook/recipe")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RecipeController {
    private final RecipeService recipeService;

    private final AuthTokenFilter authTokenFilter;
    private final JwtUtils jwtUtils;

    public RecipeController(RecipeService recipeService, JwtUtils jwtUtils, AuthTokenFilter authTokenFilter) {
        this.recipeService = recipeService;
        this.jwtUtils = jwtUtils;
        this.authTokenFilter = authTokenFilter;
    }

    @PostMapping("/add_recipe")
    public ResponseEntity<?> addRecipe(@Valid @RequestBody AddRecipeRequest addRecipeRequest, HttpServletRequest httpServletRequest) throws
            DishNotFoundException, TasteNotFoundException, CuisineNotFoundException, UsernameNotFoundException,
            IngredientNotFoundException {
        String login = jwtUtils.getLoginFromJwtToken(authTokenFilter.parseJwt(httpServletRequest));

        Recipe recipe = recipeService.saveRecipe(login, addRecipeRequest);

        return ResponseEntity.ok(new RecipeResponse(recipe.getId(),
                recipe.getDescription(), recipe.getCountPortion(), recipe.getUser().getLogin(),
                recipe.getNationalCuisine(), recipe.getDish(), recipe.getTastes(),
                recipe.getIngredients()));
    }

    @DeleteMapping("/delete_recipe")
    public ResponseEntity<?> deleteRecipe(@Valid @RequestBody DeleteRecipeRequest deleteRecipeRequest, HttpServletRequest httpServletRequest)
            throws RecipeNotFoundException, UsernameNotFoundException, NotOwnerException {
        String login = jwtUtils.getLoginFromJwtToken(authTokenFilter.parseJwt(httpServletRequest));

        recipeService.deleteRecipe(login, deleteRecipeRequest);
        return ResponseEntity.ok(new SuccessResponse
                ("Рецепт с номером " + deleteRecipeRequest.getRecipeId() + " был успешно удален!"));
    }

    @PutMapping("/update_recipe")
    public ResponseEntity<?> updateRecipe(@Valid @RequestBody UpdateRecipeRequest updateRecipeRequest, HttpServletRequest httpServletRequest) throws
            DishNotFoundException, RecipeNotFoundException,
            UsernameNotFoundException, NotOwnerException,
            CuisineNotFoundException, TasteNotFoundException, IngredientNotFoundException {
        String login = jwtUtils.getLoginFromJwtToken(authTokenFilter.parseJwt(httpServletRequest));
        recipeService.updateRecipe(login, updateRecipeRequest);
        return ResponseEntity.ok(new SuccessResponse("Рецепт с номером " + updateRecipeRequest.getRecipeId() + " был успешно обновлен!"));
    }

    @PostMapping("/search")
    public ResponseEntity<?> getAllRecipes(@RequestBody GetAllRecipesRequest getAllRecipesRequest) throws CuisineNotFoundException, DishNotFoundException {
        List<Recipe> allRecipes = recipeService.getAllRecipes(getAllRecipesRequest);
        List<RecipeResponse> recipeResponses = new ArrayList<>();
        allRecipes.forEach(recipe -> {
            recipeResponses.add(new RecipeResponse(recipe.getId(),
                    recipe.getDescription(), recipe.getCountPortion(), recipe.getUser().getLogin(),
                    recipe.getNationalCuisine(), recipe.getDish(), recipe.getTastes(),
                    recipe.getIngredients()));
        });
        return ResponseEntity.ok(recipeResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipe(@Valid @RequestBody GetRecipeRequest getRecipeRequest)
            throws RecipeNotFoundException {
        Recipe recipe = recipeService.findRecipeById(getRecipeRequest.getRecipeId());
        return ResponseEntity.ok(new RecipeResponse(recipe.getId(),
                recipe.getDescription(), recipe.getCountPortion(), recipe.getUser().getLogin(),
                recipe.getNationalCuisine(), recipe.getDish(), recipe.getTastes(),
                recipe.getIngredients()));
    }

}
