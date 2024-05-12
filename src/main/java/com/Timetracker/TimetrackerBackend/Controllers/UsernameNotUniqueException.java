package com.Timetracker.TimetrackerBackend.Controllers;

public class UsernameNotUniqueException extends RuntimeException{
    public UsernameNotUniqueException(String message) {
        super(message);
    }
    
}
