package models;

public class Ladder extends Entity {
    public Ladder(int start, int end) {
        super(start, end, EntityType.LADDER);
        if (start >= end) {
            throw new IllegalArgumentException("Ladder start must be less than end");
        }
    }
}
