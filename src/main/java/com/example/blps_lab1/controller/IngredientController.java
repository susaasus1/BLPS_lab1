package com.example.blps_lab1.controller;

import com.example.blps_lab1.dto.request.AddIngredientRequest;
import com.example.blps_lab1.dto.request.UpdateIngredientRequest;
import com.example.blps_lab1.dto.response.IngredientResponse;
import com.example.blps_lab1.dto.response.SuccessResponse;
import com.example.blps_lab1.exception.IllegalPageParametersException;
import com.example.blps_lab1.exception.IngredientAlreadyExistException;
import com.example.blps_lab1.exception.IngredientNotFoundException;
import com.example.blps_lab1.exception.ResourceNotFoundException;
import com.example.blps_lab1.model.Ingredients;
import com.example.blps_lab1.service.IngredientsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/ingredient")
@CrossOrigin(origins = "*", maxAge = 3600)
public class IngredientController {
    private final IngredientsService ingredientsService;

    public IngredientController(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }
    @Secured({"ADMIN"})
    @PostMapping()
    public ResponseEntity<?> addIngredient(@Valid @RequestBody AddIngredientRequest addIngredientRequest) throws IngredientAlreadyExistException {
        ingredientsService.saveIngredient(addIngredientRequest.getIngredientName(), addIngredientRequest.getDescription());
        return ResponseEntity.ok(new SuccessResponse("Ингредиент " + addIngredientRequest.getIngredientName() + " успешно добавлено в базу!"));
    }
    @Secured({"ADMIN"})
    @DeleteMapping()
    public ResponseEntity<?> deleteIngredient(@RequestParam("ingredientId") Long ingredientId) throws IngredientNotFoundException {
        ingredientsService.deleteIngredient(ingredientId);
        return ResponseEntity.ok(new SuccessResponse("Ингредиент с id=" + ingredientId + " успешно удален!"));
    }
    @Secured({"ADMIN"})
    @PutMapping()
    public ResponseEntity<?> updateIngredient(@RequestParam("ingredientId") Long ingredientId,
                                              @Valid @RequestBody UpdateIngredientRequest updateIngredientRequest) throws IngredientNotFoundException {
        ingredientsService.updateIngredient(ingredientId, updateIngredientRequest.getName(), updateIngredientRequest.getDescription());
        return ResponseEntity.ok(new SuccessResponse("Ингредиент с id=" + ingredientId + " успешно обновлен!"));
    }
    @Secured({"ADMIN", "USER"})
    @GetMapping()
    public ResponseEntity<?> getAllIngredients(@RequestParam(value = "page", defaultValue = "1") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size) throws IllegalPageParametersException, ResourceNotFoundException {
        return ResponseEntity.ok(ingredientsService.getIngredientsPage(page, size).getContent());
    }
    @Secured({"ADMIN", "USER"})
    @GetMapping("{ingredientId}")
    public ResponseEntity<?> getIngredient(@PathVariable Long ingredientId) throws IngredientNotFoundException {
        Ingredients ingredient = ingredientsService.getIngredient(ingredientId);
        return ResponseEntity.ok(new IngredientResponse(ingredient.getId(), ingredient.getDescription(), ingredient.getName()));
    }

}

