package com.Timetracker.TimetrackerBackend.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Timetracker.TimetrackerBackend.Models.Admin;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Admin findByName(String name);
    Admin findByPassword(String password);
    
}
