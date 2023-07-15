package models;
import java.util.*;

public class Board{
    private int size;
    Map<Integer, Integer> boardEntitiesMap;

    public Board(int n){
        this.size = n * n;
        boardEntitiesMap = new HashMap<>();
    }

    public void addEntity(BoardEntity entity){
        boardEntitiesMap.put(entity.getStartPos(), entity.getEndPos());
    }

    public int getEntityEndPos(int startPos){
        return boardEntitiesMap.getOrDefault(startPos, -1);
    }

    public int getSize(){
        return size;
    }
}