package com.example.blps_lab1.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipe")
@Getter
@Setter
@NoArgsConstructor
public class Recipe {
    @Id
    private Integer id;
    private String description;
    private Integer countPortion;

    @JoinColumn(nullable = false,name = "id_user")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @JoinColumn(nullable = false,name = "id_cuisine")
    @ManyToOne(fetch = FetchType.EAGER)
    private NationalCuisine nationalCuisine;

    @JoinColumn(nullable = false,name = "id_dish")
    @ManyToOne(fetch = FetchType.EAGER)
    private Dish dish;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "recipe_tastes",
            joinColumns = {@JoinColumn(name = "id_recipe")},
            inverseJoinColumns = {@JoinColumn(name = "id_taste")}
    )
    private List<Tastes> tastes = new ArrayList<>();



    public Recipe(String description, Integer countPortion, User user, NationalCuisine nationalCuisine, Dish dish) {
        this.description = description;
        this.countPortion = countPortion;
        this.user = user;
        this.nationalCuisine = nationalCuisine;
        this.dish = dish;
    }

}
