package com.example.blps_lab1.controller;

import com.example.blps_lab1.dto.request.AddTasteRequest;
import com.example.blps_lab1.dto.request.UpdateTasteRequest;
import com.example.blps_lab1.dto.response.DishResponse;
import com.example.blps_lab1.dto.response.SuccessResponse;
import com.example.blps_lab1.dto.response.TasteResponse;
import com.example.blps_lab1.exception.IllegalPageParametersException;
import com.example.blps_lab1.exception.ResourceNotFoundException;
import com.example.blps_lab1.exception.TasteAlreadyExistException;
import com.example.blps_lab1.exception.TasteNotFoundException;
import com.example.blps_lab1.model.Tastes;
import com.example.blps_lab1.service.TastesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/taste")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TasteController {
    private final TastesService tastesService;

    public TasteController(TastesService tastesService) {
        this.tastesService = tastesService;
    }
    @Secured({"ADMIN"})
    @PostMapping()
    public ResponseEntity<?> addTaste(@Valid @RequestBody AddTasteRequest addTasteRequest) throws TasteAlreadyExistException {
        tastesService.saveTaste(addTasteRequest.getTaste());
        return ResponseEntity.ok(new SuccessResponse("Вкус " + addTasteRequest.getTaste() + " успешно добавлен в базу!"));
    }
    @Secured({"ADMIN"})
    @DeleteMapping()
    public ResponseEntity<?> deleteTaste(@RequestParam("tasteId") Long tasteId) throws TasteNotFoundException {
        tastesService.deleteTaste(tasteId);
        return ResponseEntity.ok(new SuccessResponse("Вкус с id=" + tasteId + " успешно удален!"));
    }
    @Secured({"ADMIN"})
    @PutMapping()
    public ResponseEntity<?> updateTaste(@RequestParam("tasteId") Long tasteId,
                                         @Valid @RequestBody UpdateTasteRequest updateTasteRequest) throws TasteNotFoundException {

        tastesService.updateTaste(tasteId, updateTasteRequest.getTaste());
        return ResponseEntity.ok(new SuccessResponse("Вкус с id=" + tasteId + " успешно обновлен!"));
    }
    @Secured({"ADMIN", "USER"})
    @GetMapping()
    public ResponseEntity<?> getAllTastes(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size) throws IllegalPageParametersException, ResourceNotFoundException {
        return ResponseEntity.ok(tastesService.getAllTaste(page, size).getContent());
    }
    @Secured({"ADMIN", "USER"})
    @GetMapping("{tasteId}")
    public ResponseEntity<?> getTaste(@PathVariable Long tasteId) throws TasteNotFoundException {
        System.out.println(tasteId);
        Tastes taste = tastesService.getTaste(tasteId);
        System.out.println(taste.getTaste());
        return ResponseEntity.ok(new TasteResponse(taste.getId(), taste.getTaste()));
    }
}
