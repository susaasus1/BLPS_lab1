package com.example.blps_lab1.controller;

import com.example.blps_lab1.dto.*;
import com.example.blps_lab1.exception.CuisineAlreadyExistException;
import com.example.blps_lab1.exception.DishAlreadyExistException;
import com.example.blps_lab1.exception.IngredientAlreadyExistException;
import com.example.blps_lab1.exception.TasteAlreadyExistException;
import com.example.blps_lab1.service.DishService;
import com.example.blps_lab1.service.IngredientsService;
import com.example.blps_lab1.service.NationalCuisineService;
import com.example.blps_lab1.service.TastesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {
    private final DishService dishService;
    private final TastesService tastesService;
    private final IngredientsService ingredientsService;
    private final NationalCuisineService nationalCuisineService;

    public AdminController(DishService dishService, TastesService tastesService, IngredientsService ingredientsService, NationalCuisineService nationalCuisineService) {
        this.dishService = dishService;
        this.tastesService = tastesService;
        this.ingredientsService = ingredientsService;
        this.nationalCuisineService = nationalCuisineService;
    }

    @PostMapping("/add_dish")
    public ResponseEntity<?> addDish(@Valid @RequestBody AddDishRequest addDishRequest) throws DishAlreadyExistException {
        dishService.saveDish(addDishRequest);
        return ResponseEntity.ok(new SuccessResponse("Блюдо " + addDishRequest.getDishName() + " успешно добавлено в базу!"));
    }

    @PostMapping("/add_taste")
    public ResponseEntity<?> addTaste(@Valid @RequestBody AddTasteRequest addTasteRequest) throws TasteAlreadyExistException {
        tastesService.saveTaste(addTasteRequest);
        return ResponseEntity.ok(new SuccessResponse("Вкус " + addTasteRequest.getTaste() + " успешно добавлено в базу!"));
    }

    @PostMapping("/add_ingredient")
    public ResponseEntity<?> addIngredient(@Valid @RequestBody AddIngredientRequest addIngredientRequest) throws IngredientAlreadyExistException {
        ingredientsService.saveIngredient(addIngredientRequest);
        return ResponseEntity.ok(new SuccessResponse("Ингредиент " + addIngredientRequest.getIngredientName() + " успешно добавлено в базу!"));
    }

    @PostMapping("/add_cuisine")
    public ResponseEntity<?> addCuisine(@Valid @RequestBody AddCuisineRequest addCuisineRequest) throws CuisineAlreadyExistException {
        nationalCuisineService.saveCuisine(addCuisineRequest);
        return ResponseEntity.ok(new SuccessResponse("Национальная кухня " + addCuisineRequest.getCuisine() + " успешно добавлено в базу!"));
    }
}
