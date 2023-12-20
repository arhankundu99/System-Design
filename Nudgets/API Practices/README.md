# API Practices
## 1. API Versioning
Following API versioning is a good practice. Advantage is that if we do some major updates in our api and the update breaks, then client can quickly revert back to using the previous version of the api.

Convention of api versioning: ```<domain>/<service_name>/api/v1/...```

## 2. API Naming
We can follow the response hierarchy to write the name of the apis

For eg., we want to return posts of an user. The response structure can be:
```json
{
    "userId": 123,
    "posts" : []
}
```
The api for this can be ```users/:userId/posts```

The last element in the api name should be the resource that you want to get or you want to post into.

The api structure also depends upon the use case, Check what are the steps that the client is following to make the api call, and design your structure in that order.

For eg., one use case can be that we get the posts of an user by selecting year and userId
1. Assuming both year and userId are required, the api can be ```/year/:year/users/:userId/posts```.
2. Assuming only year is required and userId is optional, the api can be ```/year/:year/posts?userId={userId}```


## 3. API Response structure

This readme lists a common API response structure so that there are no inconsistencies of API responses for any 2 services.


<b>Example response: </b>

```json
{
    "status": {
        "code": "<status code>",
        "message": "<generic message for client>",
        "referenceCode": "<code used for debugging purpose>" 
    },
    "results": {
        // Meta data, like the total number of pages, the current page number, etc

        "users" : [
            {
                "id": 12345,
                "name" : ""
            }
        ]
    },
    "errors": {
        // Meta Data like number of errors etc
        
        // Detailed error messages
        "fields": [
            {
                "name": "<field name>",
                "error": "<specific error of this field>"
            },
            ...
        ]
    },
    "links": [
        {
            "rel": "self",
            "href": "https://api.example.com/users"
        },
        {
            "rel": "specificUser",
            "href": "https://api.example.com/users/:userId"
        },

        // Pagination info
        {
            "rel": "prev",
            "href": "https://api.example.com/users?page=1"
        },
        {
            "rel": "next",
            "href": "https://api.example.com/users?page=3"
        },
    ]
}
```

## The status object in the response
1. ```code```: In case of success, the code would be a HTTP status code, and In case of failure, the code would be abbreviated service name which caused this error along with the error code of this service.
    
    Eg: 
    
    ```code: "SE2-401"```, this code may indicate unauthorized error with service 2

2. ```referenceCode```: In case of success, there would be no reference code, But in case of failure, our reference code would contain the service name which is already present in the ```code```, along with an unique number, which would be used to find the issue in ```logs```. So we also have to incorporate this number in logs.

    Eg:
    Let's say our service is service1 and service1 internally talks to service2. Flow is like this ```client -> service1 -> service2```
    
    ```referenceCode: "SE2-10389"``` from service1, An error has occured in service2, So we can search for this log to debug easily. 
