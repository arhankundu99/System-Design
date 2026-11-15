package services;

import models.Subscriber;
import models.Topic;

public class SubscriberService {
    private final BrokerService brokerService;
    public SubscriberService(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    public void subscribe(Subscriber subscriber, Topic topic) {
        brokerService.subscribe(subscriber, topic);
    }

    public String consume(Subscriber subscriber, Topic topic) {
        return brokerService.consume(subscriber, topic);
    }
}
