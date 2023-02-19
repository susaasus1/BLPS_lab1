package com.example.blps_lab1.repository;

import com.example.blps_lab1.model.Dish;
import com.example.blps_lab1.model.NationalCuisine;
import com.example.blps_lab1.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByNationalCuisine(NationalCuisine nationalCuisine);

    List<Recipe> findAllByNationalCuisineAndDish(NationalCuisine nationalCuisine, Dish dish);

    List<Recipe> findAllByDish(Dish dish);

    List<Recipe> findAllById(Iterable<Long> longs);
}
