package models;
import java.util.UUID;
public class Venue {
    String id;
    String name;
    String address;
    int lat, lon;
    List<Screen> screens;

    public Venue(String id, String name, String address, int lat, int lon, int capacity) {
        this.id = UUID.randomUUID.toString();
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getLat() {
        return lat;
    }

    public int getLon() {
        return lon;
    }

    public void setScreens(List<Screen> screens) {
        this.screens = screens;
    }

    public void addScreen(Screen screen) {
        this.screens.add(screen);
    }
}