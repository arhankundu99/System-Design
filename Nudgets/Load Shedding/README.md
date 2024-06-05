# Load Shedding

Sudden spike in traffic causes pods to restart. To handle the traffic, we can do auto scaling, but this also takes time and requests during this period may not be handled gracefully.

I discuss two techniques here:
- Throttling
- Rate limiting

## Throttling
```
Throttling basically refers to limiting the number of inflight requests.
```

```go
package main

import (
    "net/http"
    "sync/atomic"

    "github.com/gin-gonic/gin"
)

// ThrottleMiddleware creates a middleware to limit the number of in-flight requests
func ThrottleMiddleware(maxInFlightRequests int32) gin.HandlerFunc {
    var currentInFlightRequests int32

    return func(c *gin.Context) {
        // Increment the number of in-flight requests
        current := atomic.AddInt32(&currentInFlightRequests, 1)
        defer atomic.AddInt32(&currentInFlightRequests, -1)

        // Check if the current in-flight requests exceed the allowed maximum
        if current > maxInFlightRequests {
            // If limit exceeded, respond with 429 Too Many Requests
            c.AbortWithStatusJSON(http.StatusTooManyRequests, gin.H{
                "error": "Server is too busy, please try again later.",
            })
            return
        }

        // If under the limit, process the request
        c.Next()
    }
}

func main() {
    router := gin.Default()

    // Configure the throttle middleware with a limit of 100 concurrent requests
    router.Use(ThrottleMiddleware(100))

    // Example route
    router.GET("/test", func(c *gin.Context) {
        c.JSON(http.StatusOK, gin.H{
            "message": "Hello from the server!",
        })
    })

    // Start the server on port 8080
    router.Run(":8080")
}

```


## Rate limiting
```
Rate limiting refers to limiting the number of concurrent requests.
```

### How rate limiting works

One of the famous technique is the `token` technique.

Imagine the `limit rps = 10`

#### First Request Arrival (T = 0.5 seconds):

5 requests arrive at half a second.

Each request consumes 1 token, removing 5 tokens from the bucket, leaving 5 tokens remaining.


#### Next Request Arrival (T = 1 second):
Before processing these requests, the limiter calculates how many tokens to replenish since the last check:
Since half a second has passed since the first batch of requests and the rate is 10 tokens per second, 
`0.5 × 10 = 5` tokens are due for replenishment.

If only 5 tokens were used, the bucket would be refilled back to 10 tokens, accommodating all subsequent requests up to the bucket’s limit.


```go
package main

import (
    "net/http"
    "github.com/gin-gonic/gin"
    "golang.org/x/time/rate"
)

// Create a new rate limiter that allows 5 requests per second with a burst capacity of 10
var limiter = rate.NewLimiter(5, 10)

func main() {
    router := gin.Default()
    router.Use(RateLimitMiddleware())

    // Define routes
    router.GET("/", func(c *gin.Context) {
        c.String(http.StatusOK, "Hello, World!")
    })

    // Start the server
    router.Run(":8080")
}

// RateLimitMiddleware creates a middleware that limits number of requests
func RateLimitMiddleware() gin.HandlerFunc {
    return func(c *gin.Context) {
        if !limiter.Allow() {
            c.AbortWithStatusJSON(http.StatusTooManyRequests, gin.H{
                "error": "Rate limit exceeded. Please try again later.",
            })
            return
        }
        c.Next()
    }
}

```