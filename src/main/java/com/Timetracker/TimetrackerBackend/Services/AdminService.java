package com.Timetracker.TimetrackerBackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;


import com.Timetracker.TimetrackerBackend.Models.Admin;
import com.Timetracker.TimetrackerBackend.Repositories.AdminRepository;


@Service


public class AdminService {
    private final MongoOperations mongoOperations;

    public AdminService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }
    

    @Autowired
    private AdminRepository adminRepository;

    public Admin addAdmin(Admin admin){
        return mongoOperations.insert(admin);
    }

    public Admin getAdminByName(String username){
        return adminRepository.findByName(username);
    }

    public Admin getAdminByPassword(String password){
        return adminRepository.findByPassword(password);
    }
    public Admin getAdminById(String adminId) {
        return adminRepository.findById(adminId).orElse(null);
    }
    public Admin getAdmin(){
        return adminRepository.findByName("Admin");
    }
    
}
