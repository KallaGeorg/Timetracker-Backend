package com.Timetracker.TimetrackerBackend.Models;

import org.springframework.data.annotation.Id;


import ch.qos.logback.core.util.Duration;


public class Activity {

    @Id
    private String id;
    private String name;
    private Duration duration;
  

    public Activity() {
        
    }

    public Activity(String id, String name, Duration duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
       
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

  

       
}
