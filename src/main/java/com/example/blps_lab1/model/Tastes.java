package com.example.blps_lab1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tastes")
@Getter
@Setter
@NoArgsConstructor
public class Tastes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 32, unique = true)
    private String taste;

    public Tastes(String taste) {
        this.taste = taste;
    }
}
