# Music Service
Refer: [Google mock interview here](https://www.youtube.com/watch?v=S1DvEdR0iUo&t=638s)
## Problem statement
Build a service to track top 10 most listened songs and albums in the last one week / one month / one year, by user / country / globally.

## Estimations

### Requests scale
- 100 M app downloads.
- 40 % active users per day = 40 M active users per day.
- `40 M / (24 * 60 * 60)` = 460 RPS.

### Data scale
- Each event would contain the `song_id`, `user_id`, `timestamp` and `location`. Considering the overhead, we assume each event to be 100 bytes
- By default Kafka retains messages for 7 days.
- If consumers do not work for 7 days, the amount of data stored would be `100 * 460 * 3600 * 24 * 7` = 25GB. (I think, the default is set to 500 GB) 

Normally `N2 standard machines` are chosen, which offer good general performance. For example:

```
Machine type: n2-standard-4 (4 vCPUs, 16 GB memory)
Disk: 500 GB of SSD persistent storage
```

### Latency
It is ok if the lists are refreshed every hour. So we can go for `batch processing` every hour instead of `real time processing`.


## Architecture
- POST api which would contain the above info (song_id, user_id, etc). This api is triggered when the song is selected to play from the client side.
- The service adds the events in kafka brokers.
- Batch job runs every hour and consumes events from the brokers and adds in Data warehouses like `BigQuery`.
- Jobs which would do aggregations for each usecase and add in memory databases like redis and persistant relational / non relational databases.
- Client invokes `GET` api to get songs which reads from CDN cache. In case of cache miss / cache ttl expiry in CDN, the service would read from redis. In case of redis miss, then query to DB