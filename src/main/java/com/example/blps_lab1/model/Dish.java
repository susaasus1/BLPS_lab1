package com.example.blps_lab1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor
public class Dish {
    @Id
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, length = 512, unique = true)
    private String description;

    public Dish(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
