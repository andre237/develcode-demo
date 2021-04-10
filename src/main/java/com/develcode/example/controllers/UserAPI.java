package com.develcode.example.controllers;

import com.develcode.example.models.UserDTO;
import com.develcode.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserAPI {

    private final UserRepository userRepo;

    @Autowired
    public UserAPI(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO user) {
        userRepo.save(user.parseDTO());
        return ResponseEntity.created(URI.create("/api/users/" + user.getCode())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        return userRepo.findById(id)
                .map(UserDTO::parseOriginal)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
