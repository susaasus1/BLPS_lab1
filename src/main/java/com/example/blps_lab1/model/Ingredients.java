package com.example.blps_lab1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
@NoArgsConstructor
public class Ingredients {
    @Id
    private Long id;
    private String name;
    private String description;

    public Ingredients(String name, String description) {
        this.name = name;
        this.description = description;
    }


}
