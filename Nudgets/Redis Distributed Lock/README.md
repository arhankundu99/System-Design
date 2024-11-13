# Redis Distributed Lock
Read the documentation [here](https://redis.io/docs/latest/develop/use/patterns/distributed-locks/)

Redis implements the `distributed lock` using `redlock` algorithm.

Redlock Nodejs implementation [here](https://github.com/mike-marcacci/node-redlock)

## Case 1: Single instance of redis
- When a client A wants to acquire a lock on a value `lock_key`, set a key `lock_key` with a value `lock_value` and a `timeout`.
- Now another client B wants to acquire a lock, it sees that the same `lock_key` exists, so it can't acquire the lock.
- Client A does the work and releases the lock using the `lock_value`.

### But why is `lock_value` needed here?
- Imagine this scenario, Client A acquires the lock.
- Lock is released due to `timeout`.
- Client B is now able to acquire the lock, acquires it.
- Now Client A releases the lock which was held by Client B.
- If we have a lock value which would be required when releasing the lock, this would be avoided.

## Case 2: Distrbuted redis
- We have N redis masters. (Say N = 5)
- In order to acquire a lock, the client gets the current time in milliseconds.
- It tries to acquire the lock in N redis masters sequentially.
- During this, the client uses a `timeout` which is small compared to `lock release time`. For example if the `auto-release time` is `10 seconds`, the timeout could be in the `~ 5-50 milliseconds range`.
- The client computes how much time elapsed in order to acquire the lock, by subtracting from the current time the timestamp obtained in step 2. `If and only if the client was able to acquire the lock in the majority of the instances (at least 3)`, and the total time elapsed to acquire the lock is less than lock validity time, the lock is considered to be acquired.
- If the lock was acquired, then the `lock validity time` is considered to be the `initial validity time - time elapsed`

## Usage of nodejs library for redlock

```javascript
import Client from "ioredis";
import Redlock from "redlock";

const redisA = new Client({ host: "a.redis.example.com" });
const redisB = new Client({ host: "b.redis.example.com" });
const redisC = new Client({ host: "c.redis.example.com" });

const redlock = new Redlock(
  // You should have one client for each independent redis node
  // or cluster.
  [redisA, redisB, redisC],
  {
    // The expected clock drift; for more details see:
    // http://redis.io/topics/distlock
    driftFactor: 0.01, // multiplied by lock ttl to determine drift time

    // The max number of times Redlock will attempt to lock a resource
    // before erroring.
    retryCount: 10,

    // the time in ms between attempts
    retryDelay: 200, // time in ms

    // the max time in ms randomly added to retries
    // to improve performance under high contention
    // see https://www.awsarchitectureblog.com/2015/03/backoff.html
    retryJitter: 200, // time in ms

    // The minimum remaining time on a lock before an extension is automatically
    // attempted with the `using` API.
    automaticExtensionThreshold: 500, // time in ms
  }
);
```

### Acquiring a lock
```javascript
// Acquire a lock.
let lock = await redlock.acquire(["a"], 5000);
try {
  // Do something...
  await something();

  // Extend the lock. Note that this returns a new `Lock` instance.
  lock = await lock.extend(5000);

  // Do something else...
  await somethingElse();
} finally {
  // Release the lock.
  await lock.release();
}
```

