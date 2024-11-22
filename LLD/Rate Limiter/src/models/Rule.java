package models;

public class Rule {
    String path;
    String key;
    int rateLimit; // per minute

    public Rule(String path, String key, int rateLimit) {
        this.path = path;
        this.key = key;
        this.rateLimit = rateLimit;
    }

    public String getPath() {
        return path;
    }

    public String getKey() {
        return key;
    }

    public int getRateLimit() {
        return rateLimit;
    }

}
