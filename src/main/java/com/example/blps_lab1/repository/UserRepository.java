package com.example.blps_lab1.repository;

import com.example.blps_lab1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT * FROM users" +
            " WHERE upper(users.login)=upper(:login)", nativeQuery = true)
    Optional<User> findByLogin(@Param("login") String login);

    @Query(value = "SELECT * FROM users" +
            " WHERE upper(users.email)=upper(:email)", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

}
