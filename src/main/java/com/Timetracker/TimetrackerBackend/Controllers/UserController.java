package com.Timetracker.TimetrackerBackend.Controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Timetracker.TimetrackerBackend.Models.Activity;
import com.Timetracker.TimetrackerBackend.Models.User;
import com.Timetracker.TimetrackerBackend.Models.UserLoginRequest;
import com.Timetracker.TimetrackerBackend.Repositories.UserRepository;
import com.Timetracker.TimetrackerBackend.Services.UserService;
@CrossOrigin(origins="http://localhost:5173")
@RestController
public class UserController {


    @Autowired
    private UserRepository userRepository;

    private UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    private String getRoot(){
        return "{'Hello': 'Timetracker'}";
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }
    @GetMapping("/user/{userId}/activities")
    public List<Activity> getUserActivities(@PathVariable String userId) {
       Optional<User> userOptional = userRepository.findById(userId);

       if(userOptional.isPresent()) {
        User user = userOptional.get();
        List<Activity> activities = user.getActivities();
        System.out.println("Activities: " + activities);
        return activities;
    }
    else{
        return Collections.emptyList();
    }
}


    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
        
    }
   

@PostMapping("/user/login")
public ResponseEntity<Object> login(@RequestBody UserLoginRequest loginRequest){
    
    User user = userService.getUserByUserName(loginRequest.getUsername());

    
    if(user != null && user.getPassword().equals(loginRequest.getPassword())){

        return new ResponseEntity<>(user, HttpStatus.OK);
    } else {
        
        return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }
}
     @PatchMapping("/user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody User userUpdates) {
        try {
            User existingUser = userService.getUserById(userId);
            if (existingUser == null) {
                return ResponseEntity.notFound().build(); 
            }

    
            if (userUpdates.getFirstname() != null) {
                existingUser.setFirstname(userUpdates.getFirstname());
            }
            if (userUpdates.getLastname() != null) {
                existingUser.setLastname(userUpdates.getLastname());
            }
            if (userUpdates.getActivities() != null) {
                existingUser.setActivities(userUpdates.getActivities());
            }
           
            User updatedUser = userService.updateUser(existingUser);
            return ResponseEntity.ok(updatedUser);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with the provided ID already exists");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user");
        }
    }
}