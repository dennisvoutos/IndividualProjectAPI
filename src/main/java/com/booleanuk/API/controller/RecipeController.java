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

import java.util.ArrayList;
import java.util.Arrays;
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
    @GetMapping("/search/{prompt}")
    public List<Recipe> searchForRecipe(@PathVariable String prompt){
        //the prompt will check inside both ingredients and recipe titles for a match.
        List<Recipe> result = new ArrayList<>();
        for(Recipe recipe:this.recipeRepository.findAll()){
            if(recipe.getTitle().toLowerCase().contains(prompt.toLowerCase())){
                result.add(recipe);
            }else {
                for(Ingredient ingredient:recipe.getIngredients()){
                    if(ingredient.getName().toLowerCase().contains(prompt.toLowerCase())){
                        result.add(recipe);
                    }
                }
            }
        }
        return result;
    }
    public record RecipeRequest(String title, String description, Ingredient[] ingredients, int userId){}
    @PostMapping
    public Recipe createRecipe(@RequestBody RecipeRequest recipe){
        System.out.println(recipe.userId +"\n"+recipe.title);
        User creator = this.userRepository.findById(recipe.userId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found when creating recipe" ));
        Recipe added = new Recipe(recipe.title, recipe.description);
        if(recipe.ingredients!= null) {
            for (Ingredient ingredient : recipe.ingredients) {
                ingredient.setUp();
                added.getIngredients().add(ingredient);
                ingredient.getRecipes().add(added);
            }
        }
        added.setCreator(creator);
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
    public Recipe updateRecipe(@PathVariable int id, @RequestBody RecipeRequest recipe){
        Recipe updated = this.getRecipeById(id);
        updated.setDescription(recipe.description);
        updated.setTitle(recipe.title);
        ArrayList<Ingredient> newIngredients = new ArrayList<>(Arrays.asList(recipe.ingredients));
        updated.setIngredients(newIngredients);
        return this.recipeRepository.save(updated);
    }
}
