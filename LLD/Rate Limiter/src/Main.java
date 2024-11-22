import controllers.RateLimitController;
import strategies.IRateLimitStrategy;
import strategies.TokenBucketRateLimitStrategy;

public class Main {
    public static void main(String[] args) {
        IRateLimitStrategy rateLimitStrategy = new TokenBucketRateLimitStrategy();
        RateLimitController rateLimitController = new RateLimitController(rateLimitStrategy);

        rateLimitController.registerRule("/posts", "userId", 4);
        System.out.println(rateLimitController.isAllowed("/posts", "userId", "1", 1));
        System.out.println(rateLimitController.isAllowed("/posts", "userId", "1", 2));
        System.out.println(rateLimitController.isAllowed("/posts", "userId", "1", 3));
        System.out.println(rateLimitController.isAllowed("/posts", "userId", "1", 4));
        System.out.println(rateLimitController.isAllowed("/posts", "userId", "1", 5));
        
        System.out.println(rateLimitController.isAllowed("/posts", "userId", "1", 62));




    }
}
