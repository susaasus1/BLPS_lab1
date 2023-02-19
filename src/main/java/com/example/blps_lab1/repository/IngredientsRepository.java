package com.example.blps_lab1.repository;

import com.example.blps_lab1.model.Ingredients;
import com.example.blps_lab1.model.Tastes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredients, Long> {
    @Query(value = "SELECT * FROM ingredients" +
            " WHERE upper(ingredients.name)=upper(:name)", nativeQuery = true)
    Optional<Ingredients> findByIngredient(@Param("name") String name);
}
