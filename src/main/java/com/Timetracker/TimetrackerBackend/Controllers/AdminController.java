package com.Timetracker.TimetrackerBackend.Controllers;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.Timetracker.TimetrackerBackend.Models.Admin;
import com.Timetracker.TimetrackerBackend.Models.AdminLoginRequest;
import com.Timetracker.TimetrackerBackend.Models.User;
import com.Timetracker.TimetrackerBackend.Services.AdminService;
import com.Timetracker.TimetrackerBackend.Services.UserService;


@CrossOrigin(origins="http://localhost:5173")
@RestController
public class AdminController {
    public AdminController() {
        
    }

    @Autowired
    private AdminService adminService;
  
   
    @Autowired
    private UserService userService;
    
    
    public AdminController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/admin")
    public Admin addAdmin(@RequestBody Admin admin) {
        return adminService.addAdmin(admin);
    } 
    @PostMapping("/admin/login")
    public ResponseEntity<Object> login(@RequestBody AdminLoginRequest loginRequest){
        Admin admin = adminService.getAdminByName(loginRequest.getUsername());

        if(admin != null && admin.getPassword().equals(loginRequest.getPassword()) && admin.getName().equals(loginRequest.getUsername())) {
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }
 @GetMapping("/admin")
    public Admin getAdmin() {
        return adminService.getAdmin();
    }
  
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsersResponseEntity() {
        List<User> users = userService.getUsers(); 
        return ResponseEntity.ok(users); 
    };
}
 

