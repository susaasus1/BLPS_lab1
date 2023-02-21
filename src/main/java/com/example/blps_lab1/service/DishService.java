package com.example.blps_lab1.service;

import com.example.blps_lab1.dto.AddDishRequest;
import com.example.blps_lab1.exception.DishAlreadyExistException;
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


    public Dish findDishByName(String name) throws DishNotFoundException {
        return dishRepository.findDishByName(name).orElseThrow(() -> new DishNotFoundException("Блюдо " + name + " не найдено"));
    }

    public void saveDish(AddDishRequest addDishRequest) throws DishAlreadyExistException {
        Dish dish = new Dish(addDishRequest.getDishName(), addDishRequest.getDescription());
        if (dishRepository.existsDishByName(addDishRequest.getDishName()))
            throw new DishAlreadyExistException("Блюдо " + addDishRequest.getDishName() + " уже есть в базе данных!");
        dishRepository.save(dish);
    }
}
