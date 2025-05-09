# TLS (Transport layer security)

## Flow between client and server

### 1. Client sends "Client hello" to the server
The client hello message from the client contains
- The TLS version the client supports.
- A list of supported cipher suits
- A client random

```
Client -> Server: Client hello
TLS Version: 1.2
Cipher Suites: [TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, TLS_RSA_WITH_AES_128_GCM_SHA256]
Client Random: 0x4a5b6c...
```

```
TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384

Here’s what it means:
- TLS: The protocol using the cipher suite.
- ECDHE: Elliptic Curve Diffie-Hellman Ephemeral (key exchange).
- RSA: Used for authentication.
- AES_256_GCM: Advanced Encryption Standard with 256-bit key size and GCM mode (symmetric encryption).
- SHA384: Secure Hash Algorithm for message authentication.
```

#### Flow with the cipher suite:
- Your browser (client) says: “Hi server, I support `TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384`.”

- The server replies: “Cool, I support that too. Here’s my RSA certificate.”

Your browser:

- Verifies the certificate (RSA)

- Initiates an ECDHE key exchange, and they both agree on a shared secret key

Now, both browser and server:

- Use AES-256-GCM to encrypt messages
- Use SHA-384 for PRF

Everything we send and receive (your account info, transactions, etc.) is now encrypted and authenticated using this suite.

### 2. Server sends "Server hello" to the client
The server hello includes
- The chosen cipher suite
- The server's certificate
- A randomly generated number

```
Server -> Client: Server hello
Chosen Cipher Suite: TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384
Server certificate: [Server's public key certificate]
Server random: 0x1a2b3c....
```

### 3. Client verifies the server's certificate
In this step, the client
- Checks the server's certificate against a trusted certificate authority (CA).
- Ensures that the certificate is not expired or been revoked.

### 4. Client generates a pre-master secret (Example of RSA)
- Pre-master secret is generated by the client.
- Client encrypts the premaster secret using server's public certificate key and sends it to the server.
- Server decrypts the premaster key using the private key that it has.

### 5. Master key generation on both client and server
```
master_key = PRF(pre_master_key + client_random + server_random)
```

<b>Why not use the premaster key instead of generating master key since the premaster key is encrypted?</b>

Imagine an attacker gets access to the premaster secret (0xABC123...).

If session keys were derived directly from the premaster secret, the attacker could decrypt any session using that secret.

But in TLS, the attacker still needs the exact client/server randoms and to replicate the PRF computation to get the right master secret and session keys.

So the attacker needs to have the following to generate the master key:
- Premaster key
- Client random
- Server random

### 6. Generation of session keys
Both the client and server generate session keys for encryption and decryption using `master secret`

```
Session keys = PRF(master_secret + "key_expansion" + client random + server random)
```

The session keys include

- Client write key: Used by client to encrypt data sent to the server.
- Server write key: Used by server to encrypt data sent to the client.
- And some message authentication keys
![session_keys](images/session%20keys.png)


### 7. Client finished
The client sends a finished message, encrypted with the session key.

### 8. Server finished
The server sends a finished message, encrypted with the session key.

### 9. Handshake is complete
TLS handshake is completed at this stage. Now the actual data can be exchanged between the client and the server.



## What happens if an attacker gets access to the premaster secret, client random and server random. (Assuming the client server uses RSA key exchange)
- In RSA Key exchange, the client generates a premaster secret, encrypts with the server's public key and then sends it to the server. 

- So if the attacker gets access to the premaster secret or the server's certificate's private key from which it can decrypt the encrypted premaster secret, client and server random, then the attacker can generate session keys and intercept the traffic.

- TLS 1.3 removes RSA key exchange due to this security flaw.

- <b>TLS with RSA key exchange is insecure and outdated.</b>

## Use TLS 1.3 with ECDHE key exchange for better security

Here each party generates a private key and a public key using elliptic curve cryptography, and then they use each other's public keys to compute the same premaster secret.

```
📲 Client generates:
Private key: d_client = 0xA1B2C3D4... (a random 256-bit number)

Public key: Q_client = d_client * G
```

```
🖥️ Server generates:
Private key: d_server = 0x1F2E3D4C...

Public key: Q_server = d_server * G
```
```
📤 The server sends Q_server to the client in its ServerKeyExchange.

📤 The client sends Q_client to the server in its ClientKeyExchange.
```

Now on the client
```
premaster_secret = d_client * Q_server
                 = d_client * (d_server * G)
                 = d_client * d_server * G
```

And one the server
```
premaster_secret = d_server * Q_client
                 = d_server * (d_client * G)
                 = d_server * d_client * G
```


Here the attacker has to break into client / server to get the private key to generate the master key, which makes it more secure than RSA based key exchange where if the attacker has access to the private key of server's certificate along with client random and server random, it can generate premaster certificate, which can be used to generate master secret.


## What does a certificate contain
- Version: The version of the X.509 standard used. Commonly, version 3 is used.

- Serial Number: A unique identifier for the certificate, issued by the certificate authority (CA).

- Signature Algorithm: The algorithm used by the CA to sign the certificate. For example, SHA256 with RSA encryption or ECDSA.

- Issuer: The organization or entity that issued the certificate (usually a trusted Certificate Authority, CA).

Validity Period:
- Not Before: The date and time when the certificate becomes valid.
- Not After: The expiration date and time when the certificate is no longer valid.

- Subject: The entity that the certificate represents (e.g., the website, organization, or individual).

- Public Key: The public key that is being certified. The certificate proves that the entity identified by the subject owns the public key.

- Issuer's Signature: The digital signature of the issuer (the CA), which ensures the integrity and authenticity of the certificate.

## The certificate does not contain the private key

- Private keys are generally stored in key-vaults like <b>Azure Key Vault</b>

## Flow of TLS from kubernetes pods

- User makes a request to `https://example.com`
- The request reaches the `ingress controller` which is a component of kubernetes.
- The `ingress controller` does the TLS termination (Fetches the private key from azure key vault and decrypts the encrypted request coming from the browser) and then communicates to the pods using HTTP and not HTTPS. (This way, the pods does not have to get access to the certificate or the private key). The certificate lives with the `ingress controller`.

The ingress resource looks something like this
```
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: frontend-ingress
  annotations:
    nginx.ingress.kubernetes.io/ssl-redirect: "true"
spec:
  tls:
    - hosts:
        - example.com
      secretName: tls-cert-from-akv  # Comes from Key Vault via CSI or cert-manager
  rules:
    - host: example.com
      http:
        paths:
          - path: /
            backend:
              service:
                name: frontend-service
                port:
                  number: 80

```

## How does TLS resumption work?
Say the client has done a Client hello with the server. Now the client sends the encrypted premaster to different ingress controller (Ingress controller is distributed).

How does this work? Does the client need to do a full TLS handshake again? But this would eventually result in a loop if the requests go to different ingress pods everytime.

This is where `TLS Session tickets` come in.

```
🧾 session_ticket = encrypt(session_state, session_ticket_key)

session_ticket_key is only known to the server.
```

Session ticket in case of RSA key exchange contains
- The master secret
- Cipher suite
- ClientRandom, ServerRandom
- Other parameters to recreate session keys.

The server sends this encrypted session ticket to the client in the NewSessionTicket message

- The client saves this session ticket locally.

- Now when the client sends the next request, it also sends the session ticket where the ingress controller decrypts and gets the master and the necessary params to decrypt.

- All in all, session tickets are used to make the requests stateless