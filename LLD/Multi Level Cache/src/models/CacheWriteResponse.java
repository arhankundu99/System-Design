package models;

public class CacheWriteResponse{
    private int time;
    public CacheWriteResponse(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
