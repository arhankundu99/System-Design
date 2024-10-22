# Splitwise

## Functional requirements
- User should be able to create groups.
- User should be able to add an expense.
- User should be able to settle an expense.
- User should be able to view summary of payments.

## Non functional requirements
- Available
- Low latency
- Scalable

## QPS estimations
```
- 1 M monthly users
- 50% daily users
- Each user adds / settles 2 expenses per day. Each expense contains an average of 3 users
- 1M expense writes per day
- Write QPS: 12 QPS
- Peak QPS: 2 * QPS = 24 QPS

- Store data for 5 years
- 1 M writes per day = 360M writes per year = 1.8B writes in 5 years
- expense_id: 128 bit, group_id: 128 bit, amount: 32 bit, currency: 3 bytes = 24 bits, from: 128 bit = 440 bits = 55 Bytes per row = 55 * 1.8 * 10^9 = 100 GB

- expense_id: 128 bit, to: 128 bit = 256 bits = 32 Bytes, Each expense consists of average 3 users
Total size = 32 * 3 * 1.8B = 180 GB
```
## Entities
```
Users

user_id | username | email
```

```
Groups

group_id | name | image | desc


user_id | group_id
```

```
Expense

{
    expense_id,
    group_id,
    amount,
    currency,
    from,
    to: Users[],
}


For tabular form
Expense

expense_id | group_id | amount | currency | from (userid) | settled (yes / no)

expense_participants
expense_id | to (userid) | settled (yes / no)
```

## Interface or API endpoints
- POST `/splitwise/v1/groups`
```
Request header: user_id
Request body: {
    group_name, 
    group_image,
    description,
    user_ids: []
}
```

- POST `/splitwise/v1/groups/:group_id/users`
```
Request body: {
    user_ids: []
}
```

- POST `/splitwise/v1/expenses`
```
Request header: user_id
Request body: {
    expense_name,
    expense_desc,
    group_id,
    from,
    to: Users[]
}
```
- PATCH `/splitwise/v1/expenses/:expense_id`
```
Request header: user_id,
Request body: {
    from,
    settled: yes / no
}
```

- GET `/splitwise/v1/groups/:group_id/expenses`
```
Response: {
    [
        expense_name,
        expense_desc,
        group_id,
        from,
        to: Users[]
    ]
}
```

- GET `/splitwise/v1/groups/:group_id/settlements`
```
Response: {
    [
        "from",
        "to",
        "balance",
        "currency"
    ]
}
```

## Data flow
- How to calculate the group expenses?
- Create a adjacency map of bidirectinal edges.
- Add the info only for the edges which are positive.