package com.booleanuk.API.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinColumn(name = "ingredients", referencedColumnName = "id")
    private List<Ingredient> ingredients;

    @ManyToOne
    @JoinColumn(name = "creator", referencedColumnName = "id")
    @JsonIgnoreProperties("recipes")
    private User creator;


    
    public Recipe(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Recipe() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
