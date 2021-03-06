package com.develcode.example.repositories;

import com.develcode.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByCode(Long code);

}
