package com.example.blps_lab1.controller;

import com.example.blps_lab1.config.RecipeMapper;
import com.example.blps_lab1.config.jwt.AuthTokenFilter;
import com.example.blps_lab1.config.jwt.JwtUtils;
import com.example.blps_lab1.dto.AddRecipeRequest;
import com.example.blps_lab1.dto.DeleteRecipeRequest;
import com.example.blps_lab1.dto.SuccessResponse;
import com.example.blps_lab1.dto.UpdateRecipeRequest;
import com.example.blps_lab1.exception.*;
import com.example.blps_lab1.model.Recipe;
import com.example.blps_lab1.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> addRecipe(@RequestBody AddRecipeRequest addRecipeRequest, HttpServletRequest httpServletRequest) throws
            DishNotFoundException, TasteNotFoundException, CuisineNotFoundException, UsernameNotFoundException {
        String login = jwtUtils.getLoginFromJwtToken(authTokenFilter.parseJwt(httpServletRequest));

        Recipe recipe = recipeService.saveRecipe(login, addRecipeRequest);

        return ResponseEntity.ok(new SuccessResponse(recipe.toString()));
    }

    @DeleteMapping("/delete_recipe")
    public ResponseEntity<?> deleteRecipe(@RequestBody DeleteRecipeRequest deleteRecipeRequest, HttpServletRequest httpServletRequest)
            throws RecipeNotFoundException, UsernameNotFoundException, NotOwnerException {
        String login = jwtUtils.getLoginFromJwtToken(authTokenFilter.parseJwt(httpServletRequest));

        recipeService.deleteRecipe(login, deleteRecipeRequest);
        return ResponseEntity.ok(new SuccessResponse
                ("Рецепт по номеру " + deleteRecipeRequest.getRecipe_id() + " был успешно удален!"));
    }

    @PutMapping("/update_recipe")
    public ResponseEntity<?> updateRecipe(@RequestBody UpdateRecipeRequest updateRecipeRequest, HttpServletRequest httpServletRequest) throws
            RecipeNotFoundException, UsernameNotFoundException, NotOwnerException {
        String login = jwtUtils.getLoginFromJwtToken(authTokenFilter.parseJwt(httpServletRequest));
        recipeService.updateRecipe(login,updateRecipeRequest);
        return ResponseEntity.ok(new SuccessResponse("Рецепт по номеру " + updateRecipeRequest.getRecipe_id() + " был успешно обновлен!"));
    }


}
