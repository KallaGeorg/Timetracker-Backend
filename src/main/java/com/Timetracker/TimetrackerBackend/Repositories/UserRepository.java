package com.Timetracker.TimetrackerBackend.Repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Timetracker.TimetrackerBackend.Models.User;



public interface UserRepository extends MongoRepository<User, String> {
    Optional <User> findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
}
