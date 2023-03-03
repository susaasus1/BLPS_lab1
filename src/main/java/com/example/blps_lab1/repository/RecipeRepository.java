package com.example.blps_lab1.repository;

import com.example.blps_lab1.model.Dish;
import com.example.blps_lab1.model.NationalCuisine;
import com.example.blps_lab1.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByNationalCuisine(NationalCuisine nationalCuisine);

    List<Recipe> findAllByNationalCuisineAndDish(NationalCuisine nationalCuisine, Dish dish);

    List<Recipe> findAllByDish(Dish dish);

    List<Recipe> findAllById(Iterable<Long> longs);

    @Query(value = "select r from recipe r JOIN dish d on d.id = r.dish_id " +
            "JOIN national_cuisine nc on nc.id = r.cuisine_id " +
            "WHERE UPPER(nc.cuisine) like CONCAT('%', UPPER(?1),'%') and " +
            "UPPER(d.name) like CONCAT('%',UPPER(?2),'%')", nativeQuery = true)
    Page<Recipe> findByNationalCuisineLikeAndDishLike(String nationalCuisineFilter, String dishFilter, Pageable pageable);


}
