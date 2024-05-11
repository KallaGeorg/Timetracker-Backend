package com.Timetracker.TimetrackerBackend.Models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public class Interval {

    @Id
    private String id;
    private String startTime;
    private String endTime;
    private long seconds;
    private long minutes;
    private long hours; 
    
    public Interval(){

    }

    public Interval(String startTime, String endTime) {
        this.id = UUID.randomUUID().toString();
        this.startTime = startTime;
        this.endTime = endTime;
        calculateDuration();
    }
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public long getHours() {
        return hours;
    }

    public void setHours(long hours) {
        this.hours = hours;
    }

 
   private void calculateDuration() {
        try {
            LocalDateTime start = LocalDateTime.parse(startTime);
            LocalDateTime end = LocalDateTime.parse(endTime);
        
            Duration duration = Duration.between(start, end);
            this.hours = duration.toHours();
            long remainingSeconds = duration.getSeconds() - (this.hours * 3600);
            this.minutes = remainingSeconds / 60;
            this.seconds = remainingSeconds % 60;
        } catch (DateTimeParseException e) {
            
            System.err.println("Error parsing date-time: " + e.getMessage());
            
        }
    }
    
}
