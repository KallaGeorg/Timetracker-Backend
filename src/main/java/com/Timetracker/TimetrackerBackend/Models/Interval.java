package com.Timetracker.TimetrackerBackend.Models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public class Interval {

    @Id
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long seconds;
    private long minutes;
    private long hours; 
    
    public Interval(){

    }

    public Interval(LocalDateTime startTime, LocalDateTime endTime) {
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
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
        Duration duration = Duration.between(startTime, endTime);
        this.seconds = duration.getSeconds();
        this.minutes = duration.toMinutes();
        this.hours = duration.toHours();
    }
    
}
