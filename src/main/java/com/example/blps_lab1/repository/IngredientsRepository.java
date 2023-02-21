package com.example.blps_lab1.repository;

import com.example.blps_lab1.model.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredients, Long> {

    Optional<Ingredients> findIngredientsByName(String name);
    boolean existsIngredientsByName(String name);

}
