package com.example.blps_lab1.repository;

import com.example.blps_lab1.model.ERole;
import com.example.blps_lab1.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
