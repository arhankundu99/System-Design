package services;

import models.Topic;

public class PublisherService {
    private final BrokerService brokerService;
    public PublisherService(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    public void publish(Topic topic, String message) {
        brokerService.publishMessage(topic, message);
    }

    public void createTopic(Topic topic) {
        brokerService.createTopic(topic);
    }
}
