package strategies;

import models.Bucket;
import models.Rule;

import java.util.HashMap;
import java.util.Map;

public class TokenBucketRateLimitStrategy implements IRateLimitStrategy {
    Map<String, Map<String, Integer>> rulesMap;
    Map<String, Map<String, Map<String, Bucket>>> tokenBucketMap;
    private static final int MINUTE = 60;
    public TokenBucketRateLimitStrategy() {
        rulesMap = new HashMap<>();
        tokenBucketMap = new HashMap<>();
    }

    public boolean isAllowed(String path, String key, String value, int timestamp) {
        if (!rulesMap.containsKey(path) || !rulesMap.get(path).containsKey(key)) {
            System.out.println("Rule is not defined for path: " + path + " with key: " + key);
            return true;
        }

        int rateLimit = rulesMap.get(path).get(key);


        if (!tokenBucketMap.get(path).get(key).containsKey(value)) {
            Bucket bucket = new Bucket(path, key, value, rateLimit, timestamp);
            tokenBucketMap.get(path).get(key).put(value, bucket);
        }

        Bucket bucket = tokenBucketMap.get(path).get(key).get(value);
        if (bucket.getNumTokens() == 0) {
            // fill bucket
            int lastRefillTimeStamp = bucket.getLastRefillTimeStamp();
            if (timestamp - lastRefillTimeStamp > MINUTE) {
                bucket.setNumTokens(rateLimit);
                bucket.setLastRefillTimestamp(timestamp);
            }
        }

        if (bucket.getNumTokens() == 0) {
            return false;
        }

        bucket.setNumTokens(bucket.getNumTokens() - 1);
        return true;
    }

    public void registerRule(Rule rule) {
        if (!rulesMap.containsKey(rule.getPath())) {
            rulesMap.put(rule.getPath(), new HashMap<>());
        }

        rulesMap.get(rule.getPath()).put(rule.getKey(), rule.getRateLimit());

        if (!tokenBucketMap.containsKey(rule.getPath())) {
            tokenBucketMap.put(rule.getPath(), new HashMap<>());
        }

        if (!tokenBucketMap.get(rule.getPath()).containsKey(rule.getKey())) {
            tokenBucketMap.get(rule.getPath()).put(rule.getKey(), new HashMap<>());
        }

    }
}
