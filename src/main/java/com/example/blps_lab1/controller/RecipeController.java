package com.example.blps_lab1.controller;

import com.example.blps_lab1.config.jwt.AuthTokenFilter;
import com.example.blps_lab1.config.jwt.JwtUtils;
import com.example.blps_lab1.dto.request.*;
import com.example.blps_lab1.dto.response.RecipeResponse;
import com.example.blps_lab1.dto.response.SuccessResponse;
import com.example.blps_lab1.exception.*;
import com.example.blps_lab1.model.Recipe;
import com.example.blps_lab1.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/recipe")
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

    @Secured({"ADMIN","USER"})
    @PostMapping()
    public ResponseEntity<?> newRecipe(@Valid @RequestBody AddRecipeRequest addRecipeRequest,
                                       HttpServletRequest httpServletRequest) throws
            DishNotFoundException, TasteNotFoundException, CuisineNotFoundException, UsernameNotFoundException,
            IngredientNotFoundException {
        String login = jwtUtils.getLoginFromJwtToken(authTokenFilter.parseJwt(httpServletRequest));

        Recipe recipe = recipeService.saveRecipe(login, addRecipeRequest.getDishName(),
                addRecipeRequest.getDescription(),
                addRecipeRequest.getCountPortion(), addRecipeRequest.getNationalCuisineName(), addRecipeRequest.getTastesNames(),
                addRecipeRequest.getIngredientsNames());

        return ResponseEntity.ok(new RecipeResponse(recipe.getId(),
                recipe.getDescription(), recipe.getCountPortion(), recipe.getUser().getLogin(),
                recipe.getNationalCuisine(), recipe.getDish(), recipe.getTastes(),
                recipe.getIngredients()));
    }

    @Secured({"ADMIN","USER"})
    @DeleteMapping()
    public ResponseEntity<?> deleteRecipe(@RequestParam Long id, HttpServletRequest httpServletRequest)
            throws RecipeNotFoundException, UsernameNotFoundException, NotOwnerException {
        String login = jwtUtils.getLoginFromJwtToken(authTokenFilter.parseJwt(httpServletRequest));

        recipeService.deleteRecipe(login, id);
        return ResponseEntity.ok(new SuccessResponse
                ("Рецепт с номером " + id + " был успешно удален!"));
    }


    @Secured({"ADMIN","USER"})
    @PutMapping()
    public ResponseEntity<?> updateRecipe(@RequestParam Long id,
                                          @Valid @RequestBody UpdateRecipeRequest updateRecipeRequest,
                                          HttpServletRequest httpServletRequest) throws
            DishNotFoundException, RecipeNotFoundException,
            UsernameNotFoundException, NotOwnerException,
            CuisineNotFoundException, TasteNotFoundException, IngredientNotFoundException {
        String login = jwtUtils.getLoginFromJwtToken(authTokenFilter.parseJwt(httpServletRequest));
        recipeService.updateRecipe(login, id, updateRecipeRequest.getDishName(), updateRecipeRequest.getDescription(),
                updateRecipeRequest.getCountPortion(), updateRecipeRequest.getNationalCuisineName(),
                updateRecipeRequest.getTastesNames(), updateRecipeRequest.getIngredientsNames());
        return ResponseEntity.ok(new SuccessResponse("Рецепт с номером " + id + " был успешно обновлен!"));
    }

    @GetMapping()
    public ResponseEntity<?> getAllRecipes(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "") String nationalCuisine,
                                           @RequestParam(defaultValue = "") String dish,
                                           @RequestParam(defaultValue = "") List<String> sortList,
                                           @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        List<Recipe> allRecipes = recipeService.getAllRecipes(page, size, nationalCuisine, dish, sortList, sortOrder.toString()).getContent();
        List<RecipeResponse> recipeResponses = new ArrayList<>();
        allRecipes.forEach(recipe -> {
            recipeResponses.add(new RecipeResponse(recipe.getId(),
                    recipe.getDescription(), recipe.getCountPortion(), recipe.getUser().getLogin(),
                    recipe.getNationalCuisine(), recipe.getDish(), recipe.getTastes(),
                    recipe.getIngredients()));
        });
        return ResponseEntity.ok(recipeResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getRecipe(@PathVariable Long id)
            throws RecipeNotFoundException {
        Recipe recipe = recipeService.findRecipeById(id);
        return ResponseEntity.ok(new RecipeResponse(recipe.getId(),
                recipe.getDescription(), recipe.getCountPortion(), recipe.getUser().getLogin(),
                recipe.getNationalCuisine(), recipe.getDish(), recipe.getTastes(),
                recipe.getIngredients()));
    }

}
