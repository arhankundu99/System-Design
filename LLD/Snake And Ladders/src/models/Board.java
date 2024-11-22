package models;

import java.util.HashMap;
import java.util.Map;

public class Board {
    Map<Integer, Entity> entityMap;
    int size;
    public Board(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        entityMap = new HashMap<>();
        this.size = size;
    }

    public void placeEntity(Entity entity) {
        if (entity.getStart() > 0 && entity.getStart() < size && entity.getEnd() > 0 && entity.getEnd() < size) {
            entityMap.put(entity.getStart(), entity);
        } else {
            throw new IllegalArgumentException("Start and end positions must be in limits");
        }
    }

    public Entity getEntity(int position) {
        return entityMap.get(position);
    }

    public int getSize() {
        return size;
    }
}
