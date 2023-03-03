package com.example.blps_lab1.service;

import com.example.blps_lab1.dto.request.AddDishRequest;
import com.example.blps_lab1.exception.*;
import com.example.blps_lab1.model.Dish;
import com.example.blps_lab1.repository.DishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public void saveDish(String name, String description) throws DishAlreadyExistException {
        Dish dish = new Dish(name, description);
        if (dishRepository.existsDishByName(name))
            throw new DishAlreadyExistException("Блюдо " + name + " уже есть в базе данных!");
        dishRepository.save(dish);
    }

    public void deleteDish(Long dishId) throws DishNotFoundException {
        if (!dishRepository.existsById(dishId))
            throw new DishNotFoundException("Блюдо с id=" + dishId + " уже есть в базе данных!");
        dishRepository.deleteById(dishId);
    }

    public void updateDish(Long dishId, String name, String description) throws DishNotFoundException {
        Dish dish = dishRepository.findDishById(dishId).orElseThrow(() -> new DishNotFoundException("Блюдо с id=" + dishId + " не существует!"));
        if (name != null) {
            dish.setName(name);
        }
        if (description != null) {
            dish.setDescription(description);
        }
        dishRepository.save(dish);
    }

    public Dish getDish(Long dishId) throws DishNotFoundException {
        Dish dish = dishRepository.findDishById(dishId).orElseThrow(() -> new DishNotFoundException("Блюдо с id=" + dishId + " не существует!"));
        return dish;
    }

    public Page<Dish> getAllDish(int pageNum, int pageSize) throws IllegalPageParametersException, ResourceNotFoundException {
        if (pageNum < 1 || pageSize < 1)
            throw new IllegalPageParametersException("Номер страницы и размер страницы должны быть больше 1!");
        Pageable pageRequest = createPageRequest(pageNum - 1, pageSize);
        Page<Dish> dishes = dishRepository.findAll(pageRequest);
        if (dishes.getTotalPages() < pageNum)
            throw new ResourceNotFoundException("На указанной странице не найдено записей!");
        return dishes;
    }

    private Pageable createPageRequest(int pageNum, int pageSize) {
        return PageRequest.of(pageNum, pageSize);
    }
}
