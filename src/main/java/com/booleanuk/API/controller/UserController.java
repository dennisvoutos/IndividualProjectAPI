package com.booleanuk.API.controller;

import com.booleanuk.API.model.User;
import com.booleanuk.API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id){
        return this.userRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public User createUser(@RequestBody User user){
        if(user.getEmail() == null || user.getPassword() == null || user.getUsername() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return this.userRepository.save(user);
    }
    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable int id){
        User deleted = this.getUserById(id);
        this.userRepository.delete(deleted);
        return deleted;
    }
    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user){
        User updated = this.getUserById(id);
        if(user.getEmail() == null || user.getPassword() == null || user.getUsername() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        updated.setEmail(user.getEmail());
        updated.setPassword(user.getPassword());
        updated.setUsername(user.getUsername());
        return this.userRepository.save(updated);
    }
}
