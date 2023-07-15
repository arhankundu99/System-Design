package models;
import models.BoardEntity;
import exceptions.InvalidBoardEntityException;

public class Ladder extends BoardEntity{
    public Ladder(int startPos, int endPos){
        super(startPos, endPos);
        if(startPos >= endPos){
            throw new InvalidBoardEntityException("Ladder cannot have arguments where end position is lesser than or equal to start position.");   
        }
        
    }
}