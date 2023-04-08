import java.util.*;

public class Request{
    private String user;
    private int timeStamp;
    
    public Request(String user, int timeStamp){
        this.user = user;
        this.timeStamp = timeStamp;
    }
    
    public int getTimeStamp(){
        return timeStamp;
    }

    public String getUser(){
        return user;
    }

    @Override
    public String toString(){
        return "[User: " + user + "] [Time Stamp: " + timeStamp + "]";
    }
}