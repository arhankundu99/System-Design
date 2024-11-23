package strategies;

import models.Request;

public interface IRequestAssignmentStrategy {
    void addRequest(Request request);
    void clearAllRequests();
    Request getNextRequest();
}
