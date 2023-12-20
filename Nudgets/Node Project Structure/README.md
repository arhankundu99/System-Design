# Node project structure

Having worked with node for quite a time, this is a common project structure I came up with.

```
package.json
package-lock.js
.env
server.js
routes/
        <serviceName>/
                        api/
                            v1/
                                A/
                                    A1Route.js
                                    A2Route.js
                                B/
                                health/
                                    HealthRoute.js
                                ...
controllers/
            A/
                A1Controller.js
            B/
                B1Controller.js
            health/
                HealthController.js
            ...

fastifySchemas/
                A/
                    A1Schema.js
                B/
                ...
services/
        A/
            A1Service.js
        B/
            B1Service.js
        health/
            HealthService.js
        ...
db/
    A/
        schemas/
                A1Schema.js
                A2Schema.js
        A1Db.js
        A2Db.js
clients/
    firebase/
        firebaseClient.js
    redis/
        redisClient.js
    ...
config/
    firebase/
        firebaseConfig.js
    redis/
    env/
        envConfig.js
    ...
utils/
    AUtils.js
    BUtils.js
    commonUtils.js
    logger.js
    codes.js
    ...
repo/
    redis/
        RedisRepo.js
    S3/
        S3Repo.js
    ...
middlewares/
    auth/
        authMiddleware.js
    ...
errors/
    customError1.js
    customError2.js
    ...
globalErrorHandler/
    globalErrorHandler.js
tests/
    A/
        A1Test/
                A1.test.js
                ...
    B/
    ...
```

Initial setup for node projects.
1. Setup ```.env```file.
2. Setup ```nodemon```.
3. Setup pino logger (or any logger).
4. Setup start scripts in package.json (Eg., ```npm run dev``` should start local server connecting with local clients). And maintain differences between ```devDependencies``` and ```dependencies```.
    ```
    "scripts" : {
        "start": "node server.js",
        "dev": "nodemon server.js | pino-pretty"
        "test": ...
    }
    ```
5. Setup Global Error handler.
6. Setup envConfig.js in ```config/env```, which contains local, stage and prod config, (Setup cors config and the methods that are allowed and any other env config) 
7. For Controller, Services and Db files, try to setup classes and if possible, we can have static methods.
8. Define all the error codes (And reference codes if possible for debugging), messages in the codes.js (See API Practices README to know what should be the codes, also setup referenceCodes for all service functions). Reference codes setup can be the last setup you can follow, But try to follow logging as detailed as possible.
9. Follow JS Doc commenting if you are starting with .js, If starting with .ts, try to follow.
10. Files which export Classes, follow pascal case naming convention (First letter is capital), and files which do not export classes, follow camel case convention.
11. Setup ```HealthService```, which should return health of all the services that we are using, Example response can be:
    ```
    {
        "externalServices" : {
            "redis" : {
                "status" : "OK"
            },
            "S3" : {
                "status" : "NOT OK"
            }
        },
        "internalServices" : {
            ...
        }
    }
    ``` 
