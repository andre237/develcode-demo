package com.develcode.example.controllers;

import java.net.URI;
import java.util.Map;
import java.util.HashMap;
import javax.validation.Valid;
import com.develcode.example.models.UserDTO;
import com.develcode.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus; 
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

@RestController
@RequestMapping("/api/users")
public class UserAPI {

    private final UserRepository userRepo;

    @Autowired
    public UserAPI(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO user) {
        if (userRepo.existsByCode(user.getCode())) {
            return ResponseEntity.badRequest().body("User with code X already exists");
        } else {
            userRepo.save(user.parseDTO());
            return ResponseEntity.created(URI.create("/api/users/" + user.getCode())).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        return userRepo.findById(id)
                .map(UserDTO::parseOriginal)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        if (!userRepo.existsByCode(id)) {
            return ResponseEntity.notFound().build();
        } else {
            userRepo.deleteById(id);
            return ResponseEntity.ok("Deletion successful");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateById(@PathVariable("id") Long id, @RequestBody UserDTO user) {
        var userEntity = userRepo.findById(id);
        if (userEntity.isPresent()) {
            var oldUser = userEntity.get();
            oldUser.mergeAnother(user.parseDTO());
            userRepo.saveAndFlush(oldUser);
            return ResponseEntity.ok("Update successful");
        } else {
            return ResponseEntity.badRequest().body("User with code X does not exists");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
