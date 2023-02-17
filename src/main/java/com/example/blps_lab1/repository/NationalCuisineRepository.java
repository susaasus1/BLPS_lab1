package com.example.blps_lab1.repository;

import com.example.blps_lab1.model.NationalCuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NationalCuisineRepository extends JpaRepository<NationalCuisine, Long> {
    @Query(value = "SELECT * FROM national_cuisine" +
            " WHERE upper(national_cuisine.cuisine)=upper(:cuisine)", nativeQuery = true)
    Optional<NationalCuisine> findByCuisine(@Param("cuisine") String cuisine);
}
