package com.booleanuk.API.controller;

import com.booleanuk.API.model.Ingredient;
import com.booleanuk.API.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("ingredients")
public class IngredientController {
    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public List<Ingredient> getAllIngredients(){
        return this.ingredientRepository.findAll();
    }
    @GetMapping("/{id}")
    public Ingredient getIngredient(@PathVariable int id){
        return this.ingredientRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/{name}")
    public Ingredient getIngredientByName(@PathVariable String name){
        List<Ingredient> ingredients = this.ingredientRepository.findAll().stream().filter((item)->item.getName().equalsIgnoreCase(name)).toList();
        return ingredients.get(0);
    }
    @PostMapping
    public Ingredient createIngredient(@RequestBody Ingredient ingredient){
        return this.ingredientRepository.save(ingredient);
    }
    @PutMapping("/{id}")
    public Ingredient updateIngredient(@RequestBody Ingredient ingredient, @PathVariable int id){
        Ingredient updated = this.getIngredient(id);
        updated.setName(ingredient.getName());
        updated.setQuantity(ingredient.getQuantity());
        updated.setTypeOfQuantity(ingredient.getTypeOfQuantity());
        return this.ingredientRepository.save(updated);
    }
    @DeleteMapping("/{id}")
    public Ingredient deleteIngredient(@PathVariable int id){
        Ingredient deleted = this.getIngredient(id);
        this.ingredientRepository.delete(deleted);
        return deleted;
    }
}
