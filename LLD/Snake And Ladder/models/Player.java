package models;
import java.util.*;

public class Player{
    private String name;
    private String id;

    public Player(String name){
        id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }
}