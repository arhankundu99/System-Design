package models;

import exceptions.InvalidEntityPositionException;

public class Snake extends Entity{
    public Snake(int start, int end) throws InvalidEntityPositionException {
        super(start, end, EntityType.SNAKE);
        if (start <= end) {
            throw new InvalidEntityPositionException("Snake's start position must be greater than end position.");
        }
    }
}
