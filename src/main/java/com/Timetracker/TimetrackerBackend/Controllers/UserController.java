package com.Timetracker.TimetrackerBackend.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Timetracker.TimetrackerBackend.Models.User;
import com.Timetracker.TimetrackerBackend.Models.UserLoginRequest;
import com.Timetracker.TimetrackerBackend.Services.UserService;
@CrossOrigin("http://localhost:5173")
@RestController
public class UserController {

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

    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
        
    }
    @PostMapping("/user/login")
    public String login(@RequestBody UserLoginRequest loginRequest){
        User user = userService.getUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());

        if(user != null && user.getPassword().equals(loginRequest.getPassword()) && user.getUsername().equals(loginRequest.getUsername())){
            return "Login successful";
    }else{
        return "Invalid username or password";
    }
}


}
