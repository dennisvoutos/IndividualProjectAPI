package com.booleanuk.API.controller;

import com.booleanuk.API.model.Recipe;
import com.booleanuk.API.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.prefs.PreferenceChangeListener;

@RestController
@RequestMapping("recipes")
public class RecipeController {
    @Autowired
    private RecipeRepository recipeRepository;
    @GetMapping
    public List<Recipe> getAllRecipes(){
        return this.recipeRepository.findAll();
    }
    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable int id){
        return this.recipeRepository.findById(id)
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public Recipe createRecipe(@RequestBody Recipe recipe){
        return this.recipeRepository.save(recipe);
    }
    @DeleteMapping("/{id}")
    public Recipe deleteRecipe(@PathVariable int id){
        Recipe deleted = this.getRecipeById(id);
        this.recipeRepository.delete(deleted);
        return deleted;
    }
    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable int id, @RequestBody Recipe recipe){
        Recipe updated = this.getRecipeById(id);
        updated.setDescription(recipe.getDescription());
        updated.setTitle(recipe.getTitle());
        return this.recipeRepository.save(updated);
    }
}
