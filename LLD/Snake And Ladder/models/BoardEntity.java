package models;

public class BoardEntity{
    private int startPos, endPos;
    public BoardEntity(int startPos, int endPos){
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public int getStartPos(){
        return startPos;
    }

    public int getEndPos(){
        return endPos;
    }
}