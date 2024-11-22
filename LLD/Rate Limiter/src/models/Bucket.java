package models;

public class Bucket {
    private final String path;
    private final String key;
    private final String value;
    private int numTokens;
    private int lastRefillTimeStamp;

    public Bucket(String path, String key, String value, int numTokens, int lastRefillTimeStamp) {
        this.path = path;
        this.key = key;
        this.value = value;
        this.numTokens = numTokens;
        this.lastRefillTimeStamp = lastRefillTimeStamp;
    }

    public String getPath() {
        return path;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public int getNumTokens() {
        return numTokens;
    }

    public int getLastRefillTimeStamp() {
        return lastRefillTimeStamp;
    }

    public void setNumTokens(int numTokens) {
        this.numTokens = numTokens;
    }

    public void setLastRefillTimestamp(int lastRefillTimeStamp) {
        this.lastRefillTimeStamp = lastRefillTimeStamp;
    }

}
