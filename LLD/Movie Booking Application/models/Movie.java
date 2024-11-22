package models;
import java.util.UUID;

public class Movie {
    private String name;
    private String desc;
    private String id;

    public Movie(String name, String desc) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return this.desc;
    }
}