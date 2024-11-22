package models;

public class Location {
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistance(Location location) {
        return Math.abs(x - location.getX()) + Math.abs(y - location.getY());
    }
}
