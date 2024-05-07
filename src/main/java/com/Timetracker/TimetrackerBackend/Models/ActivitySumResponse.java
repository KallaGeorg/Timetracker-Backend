package com.Timetracker.TimetrackerBackend.Models;

public class ActivitySumResponse {
    private String activityName;
    private long sumHours;
    private long sumMinutes;
    private long sumSeconds;

    public ActivitySumResponse() {
        
    }

    public ActivitySumResponse(String activityName, long sumHours, long sumMinutes, long sumSeconds) {
        this.activityName = activityName;
        this.sumHours = sumHours;
        this.sumMinutes = sumMinutes;
        this.sumSeconds = sumSeconds;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public long getSumHours() {
        return sumHours;
    }

    public void setSumHours(long sumHours) {
        this.sumHours = sumHours;
    }

    public long getSumMinutes() {
        return sumMinutes;
    }

    public void setSumMinutes(long sumMinutes) {
        this.sumMinutes = sumMinutes;
    }

    public long getSumSeconds() {
        return sumSeconds;
    }

    public void setSumSeconds(long sumSeconds) {
        this.sumSeconds = sumSeconds;
    }
    

  
    
    
}
