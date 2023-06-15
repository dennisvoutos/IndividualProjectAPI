package com.booleanuk.API.controller;

import com.booleanuk.API.model.Ingredient;
import com.booleanuk.API.model.Recipe;
import com.booleanuk.API.model.User;
import com.booleanuk.API.repository.RecipeRepository;
import com.booleanuk.API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.prefs.PreferenceChangeListener;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("recipes")
public class RecipeController {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping
    public List<Recipe> getAllRecipes(){
        return this.recipeRepository.findAll();
    }
    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable int id){
        return this.recipeRepository.findById(id)
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    public record RecipeRequest(String title, String description, List<Ingredient> ingredients, int userId){}
    @PostMapping
    public Recipe createRecipe(@RequestBody RecipeRequest recipe){
        User creator = this.userRepository.findById(recipe.userId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Recipe added = new Recipe(recipe.title, recipe.description);
        for(Ingredient ingredient : recipe.ingredients){
            added.getIngredients().add(ingredient);
        }
        creator.getRecipes().add(added);
        return this.recipeRepository.save(added);
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
