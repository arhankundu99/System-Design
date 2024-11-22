package models;

public class CacheReadResponse<Value> {
    private Value value;
    private int time;
    public CacheReadResponse(Value value, int time) {
        this.value = value;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
