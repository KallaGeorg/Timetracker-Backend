package com.Timetracker.TimetrackerBackend.Services;

import java.util.List;


import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import com.Timetracker.TimetrackerBackend.Models.User;
import com.Timetracker.TimetrackerBackend.Repositories.UserRepository;

@Service
public class UserService {

    private final MongoOperations mongoOperations;

  
    
    private final UserRepository userRepository;
    
    public UserService(MongoOperations mongoOperations, UserRepository userRepositiory) {
        this.mongoOperations = mongoOperations;
        this.userRepository = userRepositiory;
    }

    public List<User> getUsers() {
        return mongoOperations.findAll(User.class);
    }
    public boolean isUsernameUnique(String username) {
        return userRepository.findByUsername(username) == null;
    }

    public User addUser(User user) throws Exception {
        if (!isUsernameUnique(user.getUsername())) {
            throw new Exception("Username already exists");
        }
        return userRepository.save(user);
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password).orElse(null);
    }
    public User getUserByUserName(String username){
        return userRepository.findByUsername(username);
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateUser(User updatedUser){
        return userRepository.save(updatedUser);

    }
    public void deleteActivity(String userId, String activityId) {
        Query query = Query.query(Criteria.where("userId").is(userId).and("activityId").is(activityId));
        mongoOperations.remove(query, User.class);
        
    }
  
}