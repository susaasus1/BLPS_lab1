package com.example.blps_lab1.repository;

import com.example.blps_lab1.model.Tastes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TastesRepository extends JpaRepository<Tastes, Integer> {
}
