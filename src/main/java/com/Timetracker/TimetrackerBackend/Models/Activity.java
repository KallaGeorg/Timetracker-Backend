package com.Timetracker.TimetrackerBackend.Models;

import org.springframework.data.annotation.Id;





public class Activity {

    @Id
    private String id;
    private String name;
    private long duration;
  

    public Activity() {
        
    }

    public Activity(String id, String name, long duration) {
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

  

       
}
