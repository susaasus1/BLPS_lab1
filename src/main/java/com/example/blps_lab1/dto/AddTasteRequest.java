package com.example.blps_lab1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddTasteRequest {
    @NotBlank(message = "Укажите название вкуса!")
    @Size(min = 1, max = 32, message = "Укажите название вкуса! От 1 до 32 символов")
    private String taste;

    public AddTasteRequest(String taste) {
        this.taste = taste;
    }
}
