package com.example.blps_lab1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private Integer id;
    @Column(nullable = false, length = 64, unique = true)
    private String cuisine;

    public NationalCuisine(String cuisine) {
        this.cuisine = cuisine;
    }


}
