package com.example.blps_lab1.controller;

import com.example.blps_lab1.dto.request.AddIngredientRequest;
import com.example.blps_lab1.dto.request.DeleteIngredientRequest;
import com.example.blps_lab1.dto.response.SuccessResponse;
import com.example.blps_lab1.exception.IngredientAlreadyExistException;
import com.example.blps_lab1.exception.IngredientNotFoundException;
import com.example.blps_lab1.service.IngredientsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    @PostMapping()
    public ResponseEntity<?> addIngredient(@Valid @RequestBody AddIngredientRequest addIngredientRequest) throws IngredientAlreadyExistException {
        ingredientsService.saveIngredient(addIngredientRequest);
        return ResponseEntity.ok(new SuccessResponse("Ингредиент " + addIngredientRequest.getIngredientName() + " успешно добавлено в базу!"));
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteIngredient(@Valid @RequestBody DeleteIngredientRequest deleteIngredientRequest) throws IngredientNotFoundException {
        ingredientsService.deleteIngredient(deleteIngredientRequest);
        return ResponseEntity.ok(new SuccessResponse("Ингредиент с id=" + deleteIngredientRequest.getIngredientId() + " успешно удален!"));
    }

}

