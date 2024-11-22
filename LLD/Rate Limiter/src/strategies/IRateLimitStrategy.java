package strategies;

import models.Rule;

import java.util.List;

public interface IRateLimitStrategy {
    public boolean isAllowed(String domain, String key, String value, int timestamp);
    public void registerRule(Rule rule);
}
