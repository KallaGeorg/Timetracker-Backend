package com.Timetracker.TimetrackerBackend.Controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Timetracker.TimetrackerBackend.Models.Activity;
import com.Timetracker.TimetrackerBackend.Models.ActivitySumResponse;
import com.Timetracker.TimetrackerBackend.Models.Interval;
import com.Timetracker.TimetrackerBackend.Models.User;
import com.Timetracker.TimetrackerBackend.Models.UserLoginRequest;
import com.Timetracker.TimetrackerBackend.Repositories.AdminRepository;
import com.Timetracker.TimetrackerBackend.Repositories.UserRepository;
import com.Timetracker.TimetrackerBackend.Services.UserService;
@CrossOrigin(origins="http://localhost:5173")
@RestController
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    AdminRepository adminRepository;

    private UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    private String getRoot(){
        return "{'Hello': 'Timetracker'}";
    }

    // @GetMapping("/users")
    // public List<User> getUsers() {
    //     return userService.getUsers();
    // }
    @GetMapping("/user/{userId}/activities")
    public List<Activity> getUserActivities(@PathVariable String userId) {
       Optional<User> userOptional = userRepository.findById(userId);

       if(userOptional.isPresent()) {
        User user = userOptional.get();
        List<Activity> activities = user.getActivities();
        return activities;
    }
    else{
        return Collections.emptyList();
    }
}

@GetMapping("/user/{userId}/activities/{activityId}/intervals/sum")
public ResponseEntity<Object> getActivityIntervalsSum(@PathVariable String userId, @PathVariable String activityId) {
    try {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Activity activity = user.getActivities().stream()
                .filter(act -> activityId.equals(act.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Activity not found"));

        long totalSeconds = activity.getIntervals().stream()
                .mapToLong(interval -> interval.getHours() * 3600L + interval.getMinutes() * 60L + interval.getSeconds())
                .sum();

        long totalMinutes = totalSeconds / 60;
        long totalHours = totalMinutes / 60;

        totalSeconds %= 60;
        totalMinutes %= 60;

        ActivitySumResponse activitySumResponse = new ActivitySumResponse();
        activitySumResponse.setActivityName(activity.getName());
        activitySumResponse.setSumHours(totalHours);
        activitySumResponse.setSumMinutes(totalMinutes);
        activitySumResponse.setSumSeconds(totalSeconds);

        return ResponseEntity.ok(activitySumResponse);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }
}


    

@PostMapping("/user")
public ResponseEntity<Object> addUser(@RequestBody User user) {
    try {
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    } catch (Exception ex) {
        String errorMessage = "Username already exists";
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
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
                List<Activity> exsistingActivities = existingUser.getActivities();
                if(exsistingActivities == null){
                    exsistingActivities = new ArrayList<>();
                }
                List<Activity> updatedActivities = new ArrayList<>(exsistingActivities);
                updatedActivities.addAll(userUpdates.getActivities());
                existingUser.setActivities(updatedActivities);
            }
           
            User updatedUser = userService.updateUser(existingUser);
            
            return ResponseEntity.ok(updatedUser);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with the provided ID already exists");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user");
        }
    }
    

    @PatchMapping("/user/{userId}/activities/{activityId}/intervals")
    public Interval  saveInterval(@PathVariable String userId, @PathVariable String activityId, @RequestBody Interval interval) {
      User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found")); 
      Activity activity = user.getActivities().stream()
      .filter(act ->  activityId.equals(act.getId()))
      .findFirst() 
      .orElseThrow(()-> new IllegalArgumentException("Activity not found"));

      List<Interval> intervals = activity.getIntervals();
      if (intervals == null) {
          throw new IllegalArgumentException("Activity intervals list is null");
      }

      activity.addInterval(interval);
      userRepository.save(user);
      

      return interval;
    }
    @DeleteMapping("/user/{userId}/activities/{activityId}")
    public ResponseEntity<String> deleteActivity(@PathVariable String userId, @PathVariable String activityId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
            List<Activity> activities = user.getActivities();
            if (activities == null) {
                return ResponseEntity.notFound().build();
            }
    
            boolean activityRemoved = activities.removeIf(activity -> activityId.equals(activity.getId()));
            if (!activityRemoved) {
                return ResponseEntity.notFound().build();
            }
    
            userRepository.save(user);
            
    
            return ResponseEntity.ok("Activity deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete activity");
        }
    }
    
    @DeleteMapping("/user/{userId}")
public ResponseEntity<String> deleteUser(@PathVariable String userId) {
    try {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.deleteById(userId);
        
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user");
    }
}



}

        




