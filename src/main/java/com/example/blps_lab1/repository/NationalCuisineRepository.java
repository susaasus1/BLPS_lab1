package com.example.blps_lab1.repository;

import com.example.blps_lab1.model.NationalCuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationalCuisineRepository extends JpaRepository<NationalCuisine, Integer> {
}
