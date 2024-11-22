package strategies;

import models.Rule;

import java.util.*;

public class SlidingWindowRateLimitStrategy implements IRateLimitStrategy {
    Map<String, Map<String, Map<String, Queue<Integer>>>> slidingWindowMap;
    Map<String, Map<String, Integer>> rulesMap;
    private static final int MINUTE = 60;
    public SlidingWindowRateLimitStrategy() {
        slidingWindowMap = new HashMap<>();
        rulesMap = new HashMap<>();
    }
    public boolean isAllowed(String domain, String key, String value, int timestamp) {
        if (!rulesMap.containsKey(domain) || !rulesMap.get(domain).containsKey(key)) {
            System.out.println("Rule is not defined for domain: " + domain + " with key: " + key);
            return true;
        }

        if (!slidingWindowMap.get(domain).get(key).containsKey(value)) {
            slidingWindowMap.get(domain).get(key).put(value, new LinkedList<>());
        }

        Queue<Integer> timestampQueue = slidingWindowMap.get(domain).get(key).get(value);
        int rateLimit = rulesMap.get(domain).get(key);
        while (!timestampQueue.isEmpty() && timestamp - timestampQueue.peek() > MINUTE) {
            timestampQueue.poll();
        }

        if (timestampQueue.size() == rateLimit) {
            return false;
        }
        timestampQueue.add(timestamp);
        return true;
    }
    public void registerRule(Rule rule) {
        if (!slidingWindowMap.containsKey(rule.getPath())) {
            slidingWindowMap.put(rule.getPath(), new HashMap<>());
        }

        if (!slidingWindowMap.get(rule.getPath()).containsKey(rule.getKey())) {
            slidingWindowMap.get(rule.getPath()).put(rule.getKey(), new HashMap<>());
        }

        if (!rulesMap.containsKey(rule.getPath())) {
            rulesMap.put(rule.getPath(), new HashMap<>());
        }

        rulesMap.get(rule.getPath()).put(rule.getKey(), rule.getRateLimit());

    }
}
