public class RateLimitServiceHandler extends Thread{
    RateLimiterService rateLimiterService;
    String user;
    public RateLimitServiceHandler(RateLimiterService rateLimiterService, String user){
        this.rateLimiterService = rateLimiterService;
        this.user = user;
    }

    @Override
    public void run(){
        for(int time = 0; time < 65; time++){
            Request request = new Request(user, time);
            rateLimiterService.canHit(request);
            try{
                Thread.sleep(1000);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}