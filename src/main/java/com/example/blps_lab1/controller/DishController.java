package com.example.blps_lab1.controller;

import com.example.blps_lab1.dto.request.AddDishRequest;
import com.example.blps_lab1.dto.request.UpdateDishRequest;
import com.example.blps_lab1.dto.response.DishResponse;
import com.example.blps_lab1.dto.response.SuccessResponse;
import com.example.blps_lab1.exception.DishAlreadyExistException;
import com.example.blps_lab1.exception.DishNotFoundException;
import com.example.blps_lab1.exception.IllegalPageParametersException;
import com.example.blps_lab1.exception.ResourceNotFoundException;
import com.example.blps_lab1.model.Dish;
import com.example.blps_lab1.service.DishService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/dish")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @Secured("ADMIN")
    @PostMapping()
    public ResponseEntity<?> addDish(@Valid @RequestBody AddDishRequest addDishRequest) throws DishAlreadyExistException {
        dishService.saveDish(addDishRequest.getDishName(), addDishRequest.getDescription());
        return ResponseEntity.ok(new SuccessResponse("Блюдо " + addDishRequest.getDishName() + " успешно добавлено в базу!"));
    }

    @Secured("ADMIN")
    @DeleteMapping()
    public ResponseEntity<?> deleteDish(@RequestParam("dishId") Long dishId) throws DishNotFoundException {
        dishService.deleteDish(dishId);
        return ResponseEntity.ok(new SuccessResponse("Блюдо с id=" + dishId + " успешно удалено!"));
    }

    @Secured("ADMIN")
    @PutMapping()
    public ResponseEntity<?> updateDish(@RequestParam("dishId") Long dishId,
                                        @Valid @RequestBody UpdateDishRequest updateDishRequest) throws DishNotFoundException {
        dishService.updateDish(dishId, updateDishRequest.getName(), updateDishRequest.getDescription());
        return ResponseEntity.ok(new SuccessResponse("Блюдо с id=" + dishId + " успешно обнавлено!"));
    }

    @Secured({"ADMIN", "USER"})
    @GetMapping()
    public ResponseEntity<?> getAllDishes(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size) throws IllegalPageParametersException, ResourceNotFoundException {
        return ResponseEntity.ok(dishService.getAllDish(page, size).getContent());
    }
    @Secured({"ADMIN", "USER"})
    @GetMapping("{dishId}")
    public ResponseEntity<?> getDish(@PathVariable Long dishId) throws DishNotFoundException {
        Dish dish = dishService.getDish(dishId);
        return ResponseEntity.ok(new DishResponse(dish.getId(), dish.getName(), dish.getDescription()));
    }
}
