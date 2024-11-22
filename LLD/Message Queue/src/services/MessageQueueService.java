package services;

import models.Response;

import java.util.HashMap;
import java.util.Map;

public class MessageQueueService {

    Map<String, TopicHandlerService> topicHandlerMap;
    public MessageQueueService() {
        topicHandlerMap = new HashMap<>();
    }

    public void createTopic(String topic) {
        if (topicHandlerMap.containsKey(topic)) {
            throw new IllegalArgumentException("Topic already exists");
        }

        topicHandlerMap.put(topic, new TopicHandlerService(topic));
    }

    public void addSubscriber(String topic, String subscriberId) {
        if (!topicHandlerMap.containsKey(topic)) {
            throw new IllegalArgumentException("Topic does not exist");
        }

        TopicHandlerService topicHandlerService = topicHandlerMap.get(topic);
        topicHandlerService.addSubscriber(subscriberId);
    }

    public void publish(String topic, String message) {
        if (!topicHandlerMap.containsKey(topic)) {
            throw new IllegalArgumentException("Topic does not exist");
        }
        TopicHandlerService topicHandlerService = topicHandlerMap.get(topic);
        topicHandlerService.publish(message);
    }

    public void ack(String topic, String subscriberId, int offset) {
        if (!topicHandlerMap.containsKey(topic)) {
            throw new IllegalArgumentException("Topic does not exist");
        }
        TopicHandlerService topicHandlerService = topicHandlerMap.get(topic);
        topicHandlerService.ack(subscriberId, offset);
    }

    public Response getMessage(String topic, String subscriberId) {
        if (!topicHandlerMap.containsKey(topic)) {
            throw new IllegalArgumentException("Topic does not exist");
        }
        TopicHandlerService topicHandlerService = topicHandlerMap.get(topic);
        return topicHandlerService.getMessage(subscriberId);
    }
}
