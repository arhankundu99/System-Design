package models;
import models.BoardEntity;
import exceptions.InvalidBoardEntityException;

public class Snake extends BoardEntity{
    public Snake(int startPos, int endPos){
        super(startPos, endPos);
        if(startPos <= endPos){
            throw new InvalidBoardEntityException("Snake cannot have arguments where start position is lesser than or equal to end position.");   
        }
        
    }
}