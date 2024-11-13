# Ticket Master

## Functional requirements
- Design a system which allows users to search / book events on a given day
- The users should also be able to view events.

## Non functional requirements
- No double booking
- Fault tolerant
- Available / scalable (For this, we need horizontal scaling, caching in cdn or in memory db like redis)

## Entities
```
Event schema

event_id | date | description | type
```

```
User schema

user_id | email | username
```

```
Venue schema

venue_id | address | capacity | seat_map
```

```
Ticket schema

ticket_id | event_id | venue_id | seat_id | status (sold | available | in_progress) | status_updated_at
```

```
Booking schema

booking_id | user_id | total_price | booking_status | created_at | status_updated_at
```

```
Line items schema

booking_id | ticket_id | price | discount | tax
```

## Interface or API endpoints

- GET `/ticket_master/v1/events/:event_id`
```
{
    "status": 200, 
    "message": "Event fetched successfully",
    "data": {
        "event_name",
        "event_desc",
        "event_type",
    }
}
```

- GET `/ticket_master/v1/search?keyword={keyword}&start={start_date}&end={end_date}&pageSize={page_size}&page={page_number}`
```
{
    "status": 200, 
    "message": "Events fetched successfully",
    "data": [{
        "event_name",
        "event_desc",
        "event_type",
    }]
}
```

- GET `/ticket_master/v1/events/:event_id/venues?start={start_date}&end={end_date}&pageSize={page_size}&page={page_number}`
```
{
    "status": 200,
    "message": "Venues fetched successfully",
    "data": [{
        "venue_id",
        "venue_name",
        "address",
        "capacity",
        "seat_map"
    }]
}
```

- POST `/ticket_master/v1/bookings/create`
```
Request body
{
    "user_id": user_id
    "line_items": [
        {
            "ticket_id",
            "ticket_details",
            "price"
        }
    ],
    "total_price": total_price
}
```

- POST `/ticket_master/v1/bookings/complete`
```
Request body
{
    "payment_id",
    "payment_method",
    "amount",
    "order_id"
}
```

## Data flow
- User invokes the `search` api to view events
- User clicks on an event
- The service fetches the seat map and the seats from the `ticket table` whose status is `available` or `in progress` which is expired.
- User selects the seats.
- The service marks the ticket status to `in progress` with the `status_updated_at` to current timestamp.
- User creates the booking. The booking status is `in progress`
- User has limited time to complete the booking. The service initiates a payment with an expiry (Phonepe provides this). If the user tries to do a payment after expiry, then the payment fails.

## HLD
![HLD](images/HLD.png)

Another great solution is to implement a distributed lock with a TTL (Time To Live) using a distributed system like Redis. Redis is an in-memory data store that supports distributed locks and is well-suited for high-concurrency environments. It offers high availability and can be used to implement a distributed lock mechanism for the ticket booking process. Here is how it would work:

When a user selects a ticket, acquire a lock in Redis using a unique identifier (e.g., ticket ID) with a predefined TTL (Time To Live). This TTL acts as an automatic expiration time for the lock.

If the user completes the purchase, the ticket's status in the database is updated to "Booked", and the lock in Redis is manually released by the application after the TTL.

If the TTL expires (indicating the user did not complete the purchase in time), Redis automatically releases the lock. This ensures that the ticket becomes available for booking by other users without any additional intervention.

Now our Ticket table only has two states: available and booked. Locking of reserved tickets is handled entirely by Redis. The key-value pair in Redis is the ticket ID and the value is the user ID. This way we can ensure that when a user confirms the booking, they are the user who reserved the ticket.

## Flow with Redis Distributed lock
- User choses a seat. The frontend shows the tickets which are not yet booked + which are not being currently booked.
- The backend tries to acquire a lock. If it fails, then an error message is shown to select another seat. If it suceeds, then the lock is acquired with key as ticket_id and value as userid and a ttl.
- A timer runs on the lock after which the lock is invalidated.
- User moves to payment page to do the payment. The backend checks if the lock's timeout is close. If it is close, then the backend extends the lock's timeout.
- User does the payment and once the complete_order api is called, the order is complete and the backend releases the lock. If the lock was released previously due to timeout, then the backend checks whether the ticket is still available, else a refund is initiated.

Why is the value for the lock needed? Read about redis distributed lock [here](../../Nudgets/Redis%20Distributed%20Lock/README.md)



## Blocking inventory for ecommerce 
- For ticket master, we used redis distributed lock on the ticket id.
- But for ecommerce system, the flow would be somewhat different.
- User adds the items in cart.
- User checks out. This creates an order id and the backend decrements the count of all the items in RDBMS and adds the items with their quantity in redis with a ttl with key as order_id.

Now the below cases would happen
- Payment is successful and ttl is not expired: In this case, the order service manually deletes the key from redis.
- Payment is cancelled: In this case, the order service manually deletes the key and increases the inventory or if ttl is expired, then a callback is made to increase the inventory. The order is marked as cancelled.
- redis ttl is expired: The order service increases the inventory using the callback which is called when ttl is expired. The order is marked cancelled by order service.
- Redis ttl is expired and payment is successful now: The order service checks payment is successful, but the order was marked cancelled. The order service checks if the inventory can be decremented to fulfill the order or the user is given a refund

