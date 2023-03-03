package com.example.blps_lab1.controller;

import com.example.blps_lab1.dto.request.AddCuisineRequest;
import com.example.blps_lab1.dto.request.UpdateCuisineRequest;
import com.example.blps_lab1.dto.response.CuisineResponse;
import com.example.blps_lab1.dto.response.IngredientResponse;
import com.example.blps_lab1.dto.response.SuccessResponse;
import com.example.blps_lab1.exception.CuisineAlreadyExistException;
import com.example.blps_lab1.exception.CuisineNotFoundException;
import com.example.blps_lab1.exception.IllegalPageParametersException;
import com.example.blps_lab1.exception.ResourceNotFoundException;
import com.example.blps_lab1.model.NationalCuisine;
import com.example.blps_lab1.repository.NationalCuisineRepository;
import com.example.blps_lab1.service.NationalCuisineService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/cuisine")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CuisineController {
    private final NationalCuisineService cuisineService;

    public CuisineController(NationalCuisineService cuisineService) {
        this.cuisineService = cuisineService;
    }
    @Secured({"ADMIN"})
    @PostMapping()
    public ResponseEntity<?> addCuisine(@Valid @RequestBody AddCuisineRequest addCuisineRequest) throws CuisineAlreadyExistException {
        cuisineService.saveCuisine(addCuisineRequest.getCuisine());
        return ResponseEntity.ok(new SuccessResponse("Кухня " + addCuisineRequest.getCuisine() + " успешно добавлена в базу!"));
    }
    @Secured({"ADMIN"})
    @DeleteMapping()
    public ResponseEntity<?> deleteCuisine(@RequestParam("cuisineId") Long cuisineId) throws CuisineNotFoundException {
        cuisineService.deleteCuisine(cuisineId);
        return ResponseEntity.ok(new SuccessResponse("Кухня с id=" + cuisineId + " успешно удален!"));
    }
    @Secured({"ADMIN"})
    @PutMapping()
    public ResponseEntity<?> updateCuisine(@RequestParam("cuisineId") Long cuisineId,
                                           @Valid @RequestBody UpdateCuisineRequest updateCuisineRequest) throws CuisineNotFoundException {
        cuisineService.updateCuisine(cuisineId, updateCuisineRequest.getCuisine());
        return ResponseEntity.ok(new SuccessResponse("Кухня с id=" + cuisineId + " успешно обновлен!"));
    }
    @Secured({"ADMIN", "USER"})
    @GetMapping()
    public ResponseEntity<?> getAllCuisines(@RequestParam(value = "page", defaultValue = "1") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size) throws IllegalPageParametersException, ResourceNotFoundException {
        return ResponseEntity.ok(cuisineService.getCuisinesPage(page, size).getContent());
    }
    @Secured({"ADMIN", "USER"})
    @GetMapping("{cuisineId}")
    public ResponseEntity<?> getCuisine(@PathVariable Long cuisineId) throws CuisineNotFoundException {
        NationalCuisine nationalCuisine = cuisineService.getCuisine(cuisineId);
        return ResponseEntity.ok(new CuisineResponse(nationalCuisine.getId(), nationalCuisine.getCuisine()));
    }
}
