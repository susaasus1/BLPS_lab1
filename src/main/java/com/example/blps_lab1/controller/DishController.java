package com.example.blps_lab1.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/dish")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DishController {
}
