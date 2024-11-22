# Rate limiter LLD

- Design a rate limiter with the following features.
- We can choose the rate limiting strategy like sliding window, token bucket.
- We should be able to register a rule.
- A rule contains `domain`, `key` and a `rate limit` per minute. (The rate limit should be done as per this rule)