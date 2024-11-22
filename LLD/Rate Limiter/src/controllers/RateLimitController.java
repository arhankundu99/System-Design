package controllers;

import models.Rule;
import strategies.IRateLimitStrategy;

public class RateLimitController {
    private IRateLimitStrategy rateLimitStrategy;
    public RateLimitController(IRateLimitStrategy rateLimitStrategy) {
        this.rateLimitStrategy = rateLimitStrategy;
    }

    public boolean isAllowed(String domain, String key, String value, int timestamp) {
        return rateLimitStrategy.isAllowed(domain, key, value, timestamp);
    }

    public void registerRule(String domain, String key, int rateLimit) {
        Rule rule = new Rule(domain, key, rateLimit);
        rateLimitStrategy.registerRule(rule);
    }
}
