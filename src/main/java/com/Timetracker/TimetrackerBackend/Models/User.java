package com.Timetracker.TimetrackerBackend.Models;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private List<Activity> activities;
   

    public User() {
        this.id = UUID.randomUUID().toString();  
    }


    public User( String firstname, String lastname, String email, String username, String password,
            List<Activity> activities) {
        this.id = UUID.randomUUID().toString();
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.activities = activities;
    }


 


    public String getFirstname() {
        return firstname;
    }


    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }


    public String getLastname() {
        return lastname;
    }


    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public List<Activity> getActivities() {
        return activities;
    }


    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    
}
