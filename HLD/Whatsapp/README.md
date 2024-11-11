# Whatsapp
## Functional requirements
- Users should be able to send messages to other users.
- Users should be able to create a group chat with other users.
- Users should be able to send media in their messages.
- Users should be able to send messages even when the recipient is offline. (The recipient would get the message once they are online).

## Non functional requirements
- Low latency of sending messages
- Scalable under traffic

## QPS and storage estimations
- 100 M monthly users
- 50% of them daily users = 50M users
- Users send 10 messages daily and 20% is media content.
```
Chat QPS:
50 * 10^6 * 10 / 86400 = 6000 QPS

Storage estimations
Each message is roughly 20 Bytes in size and media is 200 bytes in size
Each user sends 560 bytes of data.
Daily storage = 50 * 10^6 * 560 = 28 * 10^9 = 28 GB / day

Messages to be stored for 1 year = 28 * 30 * 12 = 10 TB
```

## Entities
- Users
```
user_id, email, name
```

- Groups
```
group_id, name, user_id

group_id, created_by
```

- Chat
```
chat_id, group_id, from, message, created_at, updated_at

chat_id, to, status (delivered, in_progress, not_delivered)
```

## API endpoints
- POST `/whatsapp/v1/groups` (We can do this via websocket protocol also)
```
accesstoken in headers

{
    "group_name",
    "users"
}
```

- PUT `/whatsapp/v1/groups` => Add or remove users (We can do this via websocket also)

- Send a message (This will be done via websockets)
```
{
    chat_id,
    group_id,
    from,
    to,
    timestamp
}
```

## Design 1
![HLD_1](images/HLD_1.png)

## Sending messages to other user when both are online
![HLD_2](images/HLD_2.png)

## Sending messages to other user when the other user is offline
In this case, we would be reading the `chat` table which has `chat_id`, `to`, `status` and we would be sending these updates to the offline user once they are online.

## Another way of doing this is to use pub / sub
![HLD_3](images/HLD_3.png)
