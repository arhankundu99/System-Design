# Authentication
This readme focusses on working and types of authentication mechanisms.

Read <a href="../Local Storage And Cookies/README.md">Local Storage And Cookies</a> for a quick refresher on local storage and cookies.


## Session based authentication
1. Client sends credentials (username, password) to server.
2. Server creates an entry in a session table which has the userId, sessionId and other details (Like expiration time).
3. Server sends this session id to the client as cookie.
4. Now client would send this cookie with each request to maintain the session.

## Token based authentication
1. Client sends credentials (username, password) to server.
2. Server creates a token which generally consists of
    
    (a) Header (It consists of the type of token and the algorithm used to create the signature). The decoded header may look like this
    
    ```json
    {
        "alg": "HS256",
        "typ": "JWT"
    }
    ```

    (b) Payload (The details of the user)
    ```json
    {
        "userId": 123,
        "name": "Jane Doe"
    }
    ```

    (c) Signature: The signature is created using the algorithm specified in the header.
    ```
    HMACSHA256(
        base64UrlEncode(header) + "." + 
        base64UrlEncode(payload),
        secret
    )
    ```
3. The token is then sent to the client which it then likely stores in local storage (We can also store in cookies depending upon our usecase).
4. And the client then may send this token (Assuming it is in local storage) for the apis that need authorization.
5. So what happens is when the server again receives the token, it decodes the token, and generates a signature using the header, payload and the secret and then it matches the signature with the signature that was generated and checks whether the token is a valid token or not.

## What to use? Session based or token based?

<table>
    <tr>
        <td>
            Session based authentication
        </td>
        <td>
            Token based authentication
        </td>
    </tr>
    <tr>
        <td>
            In session based, we would need to store the session info in the server side.
        </td>
        <td>
            We don't need to store anything here in the server side. Just a secret (Which the token validation algorithm will use to validate the token). 
        </td>
    </tr>
    <tr>
        <td>
            But in case of sessions, we have more control, We can terminate a session whenever we want from server side if we suspect anything wrong.
        </td>
        <td>
            Here we may have to wait for the token to be expired.
        </td>
    </tr>
</table>

## Refresh token
Now, We have noticed that when we login to amazon or youtube, it does not ask for our credentials everytime we visit the site. So if these sites simply use the access token or session id, then it should have a long expiry time. But if it has long expiry time, then that is definitely less secure because the attacker may obtain our access token or session id and then they would have the access to our profiles.

To not annoy users everytime (For credentials) they visit our site and to prevent attackers from using the tokens, we can use a special token called refresh tokens.

### Flow
1. User sends credentials to server. (```/auth/login``` or ```/auth/register```)
2. Server generates two tokens, access token and refresh token.
3. Server now sends requests with the access token to access resources.
4. Once the token expires, the client sends a request with the refresh token (```/auth/refresh-token```) and gets both access token and a new refresh token (Possibly as this increases security)

Refresh tokens can be generated in the same way as JWT access token or we can store the refresh token in db. Both have their pros and cons.
1. If the refresh token is generated in the same way as the JWT access token, then the attacker can use the old refresh tokens of the user which are still not expired and get new access tokens (Because we are not storing the old refresh tokens in our server right?). In this approach, we don't need to store anything in db, but we would be facing security issues.
2. Refresh token can be persisted in db. When we get a request to generate a new access token, the refresh token would be used to check for validity with the refresh token in the db and then generate a new access token and a refresh token (This token would replace the old refresh token).

I would suggest to go with the hybrid approach (JWT access token + storing refresh token in db), because there won't be frequent reads in the refresh token table. We would be only reading when the access token expires.


# NOTE (The below content of OAuth is taken from ietf site)

## OAuth (Open Authorization)
OAuth is an open standard authorization protocol that describes how unrelated servers and services can safely allow authenticated access to their resources without actually sharing the credentials.

The OAuth Protocol was originally created by a small community of web developers from a variety of websites and other internet services who wanted to solve the common problem of enabling delegated access to protected resources.

In a traditional client server model, the client uses its credentials to access server's resources. But now there is an increasing number of third party applications which require access to the server's resources.