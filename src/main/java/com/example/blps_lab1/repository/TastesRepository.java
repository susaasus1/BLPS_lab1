package com.example.blps_lab1.repository;

import com.example.blps_lab1.model.Tastes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TastesRepository extends JpaRepository<Tastes, Long> {
    @Query(value = "SELECT * FROM tastes" +
            " WHERE upper(tastes.taste)=upper(:taste)", nativeQuery = true)
    Optional<Tastes> findByTaste(@Param("taste") String taste);
}
