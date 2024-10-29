# Top K
## Requirements
Find top K videos in youtube by
- All time.
- Month
- Day
- Hour

## Non functional Requirements
- Scalable
- Available
- Low latency

## QPS estimations
```
1 B users monthly
Assuming 50% daily users
500 M users daily
Each user watches 1 hour of content daily (Average of 2 videos)

1 B media clicks daily
View QPS = 10^9 / 86400 = 12K QPS

For storage, we need ID and count
ID: 128 bit
count: 64 bit

Total size for an entry: 192 bits = 24 bytes
Storage for 10 years.
Number of videos = 1B in 10 years
Total storage = 24 GB.

We can use in-memory if we are smart enough.
```

## Entities
MediaId, count, window

## API or interfance
- GET `/top_k/v1/views?K={k}&window={window}
```
{
    "code": 200,
    "message",
    "data": {
        "videos": [
            {
                "mediaId",
                "views"
            }]
    }
}
```

## Design for top K views for all time
### Design 1
![HLD_1](images/HLD_1.png)

Problems: 
- Single point of failure.
- Not Scalable

### Design 2
![HLD_2](images/HLD_2.png)

### Design 3
In this design, each consumer is in separate consumer group
![HLD_3](images/HLD_3.png)

### Design 4
Scaling the writes because the above solution does not solve for massive amount of writes