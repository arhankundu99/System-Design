# API Rate Limiter
## Introduction
API Rate limiter allows to restrict the number of API calls in a certain time interval which allows to prevent DOS (Denial of service attacks)

A Denial of service attack is a type of cybersecurity attack where the attacker floods the server with a large amount of requests which consumes many resources and thus making the servers unavailable for the other users.

## Design
![Simple Design](/API%20Rate%20Limiter/images/API%20Rate%20Limiter%20Image%201.png)

(Source: Image taken from https://www.enjoyalgorithms.com/blog/design-api-rate-limiter)

We have to maintain counters for the requests from an user to check if the user has surpassed the limit in that time interval.

Storing these counters in durable databases like mysql, mongo, cassandra etc would be slow because the reads would be very frequent. So we can store the counters in in-memory databases like redis or memcache. We can use INCR or EXPR commands in redis for the requests in redis (Example shown below).
```javascript
const express = require('express');
const app = express();

const redis = require('redis');

// redis client which would store userid as key and number of requests as value
const userClient = redis.createClient();

const util = require('util');

userClient.get = util.promisify(userClient.get);
userClient.set = util.promisify(userClient.set);
userClient.exists = util.promisify(userClient.exists);

// redis client which would store request as key
const requestClient = redis.createClient();

const REQUEST_LIMIT_PER_USER = 5; 
const REQUESTS_TIME_INTERVAL = 60; // user can send only 5 requests in one minute

const rateLimitingMiddleware = async (req, res, next) => {
    // get the user from the request
    const user = ...

    const doesUserExist = await userClient.exists(user);
    
    // if user does not exists in redis database
    if(!doesUserExist)
        userClient.set(user, 0)

    // get the request count of the user from redis
    const requestCount = await userClient.get(user);

    if(requestCount >= REQUEST_LIMIT_PER_USER){
        return res.send("API call limit reached");
    }

    else{
        // increase the counter of requests by 1
        userClient.incr(user, 1);

        // store the request in the database
        requestClient.set(JSON.stringify(req), null);

        // set expire method for the current request
        requestClient.expire(JSON.stringify(req), REQUESTS_TIME_INTERVAL, (err, success) => {
            if(success){
                // reduce the count of requests by 1
                userClient.decr(user, 1);
            }
        })

    }

    // valid request, now handle the request in other middleware functions
    return next();

}

app.use(rateLimitingMiddleware);

```

Design using redis

![](/API%20Rate%20Limiter/images/API%20rate%20limiter%20using%20redis.png)

(Refer the java example also)

