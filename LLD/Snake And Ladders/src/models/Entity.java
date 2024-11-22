package models;

public class Entity {
    int start;
    int end;
    EntityType type;
    Entity(int start, int end, EntityType type) {
        this.start = start;
        this.end = end;
        this.type = type;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

    public EntityType getType() {
        return type;
    }
}
