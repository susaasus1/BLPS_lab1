package com.example.blps_lab1.controller;

import com.example.blps_lab1.config.jwt.AuthTokenFilter;
import com.example.blps_lab1.config.jwt.JwtUtils;
import com.example.blps_lab1.dto.AddRecipeRequest;
import com.example.blps_lab1.dto.SuccessResponse;
import com.example.blps_lab1.exception.CuisineNotFoundException;
import com.example.blps_lab1.exception.DishNotFoundException;
import com.example.blps_lab1.exception.TasteNotFoundException;
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

        recipeService.saveRecipe(login, addRecipeRequest.getDish_name(), addRecipeRequest.getDescription(),
                addRecipeRequest.getCountPortion(),
                addRecipeRequest.getNationalCuisine_name(),
                addRecipeRequest.getTastes_names());

        return ResponseEntity.ok(new SuccessResponse("Recipe added successfully!"));

    }
}
