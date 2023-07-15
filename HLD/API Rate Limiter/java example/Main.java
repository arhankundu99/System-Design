import java.util.*;

public class Main{

    public static void main(String[] args){
        RateLimiterService rateLimiterService = new RateLimiterService(5);

        new RateLimitServiceHandler(rateLimiterService, "User A").start();
        new RateLimitServiceHandler(rateLimiterService, "User B").start();
    }
}