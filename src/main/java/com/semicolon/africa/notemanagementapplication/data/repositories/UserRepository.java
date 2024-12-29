package com.semicolon.africa.notemanagementapplication.data.repositories;

import com.semicolon.africa.notemanagementapplication.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository <User, String>{
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

}
