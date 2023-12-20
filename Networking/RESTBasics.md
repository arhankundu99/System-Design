# REST

REST stands for Representational State Transfer. It is an architectural design of our software. 

## Design Principles of REST: 

### Client-Server: 
By dividing our software into client and server architecture, we allow them to evolve independently and also this increases the portability 

![](images/REST%20Client%20Server.png)

### Stateless: 
In client server architecture, the communication must be stateless in nature. That means, the request has to contain all the information and server would not store any state regarding the request.  
If the requests are stateless, then server would not have to look beyond the request and it would not have to store any state. 

But the disadvantage is that, repetitive data would be sent for each request to the server. 
Now the architecture becomes client-stateless-server architecture. 

![](images/REST%20Client%20Stateless%20Server.png)

### Cache: 
Here, data within a response to a request should contain whether the response is cacheable or not. This would increase the performance of our system. But downside is that we may send stale responses to the user. 
Now the architecture becomes client-cache-stateless-server architecture.  

![](images/REST%20Client%20Cache%20Stateless%20Server.png)

### Uniform Interface:
niform interface is a key principle in REST (Representational State Transfer) design. It means that the way a client interacts with a server should be consistent and standardized, regardless of the specific details of the server's implementation.

In other words, a uniform interface ensures that all interactions between a client and a server follow the same set of rules and patterns, regardless of the specific resources being accessed or the actions being performed.

Some examples of the elements that make up a uniform interface in REST include:

Resource identification through URI: Each resource on a server is identified by a unique URI (Uniform Resource Identifier).

HTTP methods: Clients use standard HTTP methods (such as GET, POST, PUT, DELETE) to interact with resources on the server.

Representation of resources: Resources are represented in a standardized format (such as JSON or XML) that can be easily understood by clients.

Hypermedia as the engine of application state (HATEOAS): Resources contain links to related resources, allowing clients to discover and navigate the server's resources dynamically.
By following a uniform interface, RESTful services can be more easily understood and used by clients, and can be more easily maintained and updated by developers.

![](images/Uniform%20Interface%20Example.png)

### Layered Architecture

A layered system architecture in REST means that the components of the system are organized into layers, with each layer responsible for a specific set of functionalities. This allows for better separation of concerns and easier maintenance of the system.

In a layered system architecture, each layer only interacts with the layer immediately below or above it, and not with any other layers in the system. This helps to keep the system modular and makes it easier to replace or update individual components without affecting the entire system.

![](images/Layered%20Architecture%20Example.png)
