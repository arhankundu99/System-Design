package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Subscriber;
import models.Topic;

public class BrokerService {
    Map<Topic, List<String>> topicDataMap;
    Map<Subscriber, Map<Topic, Integer>> subscriberOffsetsMap;

    public BrokerService() {
        topicDataMap = new HashMap<>();
        subscriberOffsetsMap = new HashMap<>();
    }

    public void createTopic(Topic topic) {
        if (!topicDataMap.containsKey(topic)) {
            topicDataMap.put(topic, new ArrayList<>());
        }
        else {
            throw new IllegalArgumentException("Topic already exists");
        }
    }

    public void publishMessage(Topic topic, String message) {
        if (!topicDataMap.containsKey(topic)) {
            throw new IllegalArgumentException("Topic does not exist");
        }
        topicDataMap.get(topic).add(message);
    }

    public void subscribe(Subscriber subscriber, Topic topic) {
        if (!topicDataMap.containsKey(topic)) {
            throw new IllegalArgumentException("Topic does not exist");
        }
        subscriberOffsetsMap.putIfAbsent(subscriber, new HashMap<>());
        subscriberOffsetsMap.get(subscriber).putIfAbsent(topic, 0);
    }

    public String consume(Subscriber subscriber, Topic topic) {
        if (!subscriberOffsetsMap.containsKey(subscriber) || !subscriberOffsetsMap.get(subscriber).containsKey(topic)) {
            throw new IllegalArgumentException("Subscriber is not subscribed to the topic");
        }
        int offset = subscriberOffsetsMap.get(subscriber).get(topic);
        List<String> messages = topicDataMap.get(topic);
        if (offset >= messages.size()) {
            return null;
        }

        String newMessage = messages.get(offset);
        subscriberOffsetsMap.get(subscriber).put(topic, offset + 1); // Update offset
        return newMessage;
    }
}
