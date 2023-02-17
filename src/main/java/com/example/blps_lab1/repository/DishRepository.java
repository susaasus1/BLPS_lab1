package com.example.blps_lab1.repository;

import com.example.blps_lab1.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    @Query(value = "SELECT * FROM dish" +
            " WHERE upper(dish.name)=upper(:name)", nativeQuery = true)
    Optional<Dish> findByName(@Param("name") String name);
}
