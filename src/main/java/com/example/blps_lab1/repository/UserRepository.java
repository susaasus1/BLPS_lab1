package com.example.blps_lab1.repository;

import com.example.blps_lab1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);


    boolean existsUserByLogin(String login);

    boolean existsUserByEmail(String email);
}
