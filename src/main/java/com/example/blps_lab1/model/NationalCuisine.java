package com.example.blps_lab1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "national_cuisine")
@Getter
@Setter
@NoArgsConstructor
public class NationalCuisine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 64, unique = true)
    private String cuisine;

    public NationalCuisine(String cuisine) {
        this.cuisine = cuisine;
    }


}
