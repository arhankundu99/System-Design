package models;

import java.util.UUID;

public class Request {
    private final String id;
    private final int sourceFloor;
    private final int destinationFloor;
    private final RequestSource requestSource;
    
    public Request(int sourceFloor, int destinationFloor, RequestSource requestSource) {
        this.id = UUID.randomUUID().toString();
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
        this.requestSource = requestSource;
    }
    
    public String getId() {
        return id;
    }
    
    public int getSourceFloor() {
        return sourceFloor;
    }

    
    public int getDestinationFloor() {
        return destinationFloor;
    }
    
    public RequestSource getRequestSource() {
        return requestSource;
    }
}
