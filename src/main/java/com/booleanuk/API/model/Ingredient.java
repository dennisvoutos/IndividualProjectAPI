package com.booleanuk.API.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    private int quantity;
    @Column(name = "type_of_quantity")
    private String typeOfQuantity;
    @ManyToMany
    private List<Recipe> recipes;

    public Ingredient() {
    }

    public Ingredient(String name, int quantity, String typeOfQuantity) {
        this.name = name;
        this.quantity = quantity;
        this.typeOfQuantity = typeOfQuantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTypeOfQuantity(String typeOfQuantity) {
        this.typeOfQuantity = typeOfQuantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getTypeOfQuantity() {
        return typeOfQuantity;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
