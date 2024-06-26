package com.Timetracker.TimetrackerBackend.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;





public class Activity {

    @Id
    private String id;
    private String name;
    private List<Interval> intervals;
    private long totalDuration;
  
  

    public Activity() {
        
    }


    public Activity(String name, List<Interval> intervals) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.intervals = new ArrayList<>();
       
     
    }
    public void addInterval(Interval interval) {
        if (intervals == null) {
            intervals = new ArrayList<>();
        }
        intervals.add(interval);
        
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


    public List<Interval> getIntervals() {
        return intervals;
    }
    public void setIntervals(List<Interval> intervals) {
        this.intervals = intervals;
        
    }


    public long getTotalDuration() {
        return totalDuration;
    }


    public void setTotalDuration(long totalDuration) {
        this.totalDuration = totalDuration;
    }

    
       
}
