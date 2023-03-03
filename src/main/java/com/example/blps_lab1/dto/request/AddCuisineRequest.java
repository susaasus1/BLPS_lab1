package com.example.blps_lab1.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddCuisineRequest {
    @NotBlank(message = "Укажите название кухни!")
    @Size(min = 1, max = 64, message = "Укажите название кухни! От 1 до 64 символов")
    private String cuisine;

    public AddCuisineRequest(String cuisine) {
        this.cuisine = cuisine;
    }
}
