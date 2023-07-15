package model;

import java.util.*;
public class Meeting{
    private int startTime;
    private int endTime;
    private String id;

    public Meeting(int startTime, int endTime){
        this.id = UUID.randomUUID().toString();
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getStartTime(){
        return this.startTime;
    }

    public int getEndTime(){
        return this.endTime;
    }

    @Override
    public String toString(){
        return "ID: " + this.id + "\n" + 
               "Start time: " + this.startTime + "\n" +
               "End time: " + this.endTime;
    }
}