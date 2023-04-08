import java.util.*;
import java.util.concurrent.*;

public class RateLimiterService{
    Map<String, List<Request>> map;
    int rateLimit; // rateLimit per minute

    public RateLimiterService(int rateLimit){
        this.rateLimit = rateLimit;
        map = new ConcurrentHashMap<>();
    }

    public boolean canHit(Request request){
        String user = request.getUser();
        if(!map.containsKey(user)){
            map.put(user, new ArrayList<>());
        }

        if(map.get(user).size() < rateLimit){
            map.get(user).add(request);
            System.out.println(request + " can be hit successfully");
            return true;
        }
        else{
            for(int i = 0; i < map.get(user).size(); i++){
                int timeDiff = request.getTimeStamp() - map.get(user).get(i).getTimeStamp();
                if(timeDiff > 60){
                    System.out.println(request + " is being removed.");
                    map.get(user).remove(i);
                }
            }

            if(map.get(user).size() < rateLimit){
                System.out.println(request + " can be hit successfully after removing the expired requests.");
                map.get(user).add(request);
                return true;
            }

            System.out.println(request + " cannot be hit successfully");
            return false;
        }
    }
}