package com.example.blps_lab1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tastes")
@Getter
@Setter
@NoArgsConstructor
public class Tastes {
    @Id
    private Integer id;
    @Column(nullable = false, length = 32, unique = true)
    private String taste;
    @ManyToMany(mappedBy = "tastes")
    private List<Recipe> recipeList = new ArrayList<>();

}
