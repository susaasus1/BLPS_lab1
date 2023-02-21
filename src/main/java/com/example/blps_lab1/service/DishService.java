package com.example.blps_lab1.service;

import com.example.blps_lab1.exception.DishNotFoundException;
import com.example.blps_lab1.model.Dish;
import com.example.blps_lab1.repository.DishRepository;
import org.springframework.stereotype.Service;

@Service
public class DishService {

    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }


    public Dish findDishByName(String name) throws DishNotFoundException{
       return dishRepository.findDishByName(name).orElseThrow(() -> new DishNotFoundException("Блюдо " + name + " не найдено"));
    }
}
