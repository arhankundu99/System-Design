package services;

import models.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TopicHandlerService {
    private String topicName;
    ArrayList<String> dataStream;
    private final String id;
    Map<String, Integer> offsetMap;
    public TopicHandlerService(String topicName) {
        this.topicName = topicName;
        this.dataStream = new ArrayList<>();
        this.offsetMap = new HashMap<>();
        id = UUID.randomUUID().toString();
    }

    public String getTopicName() {
        return topicName;
    }

    public String getId() {
        return id;
    }

    public void addSubscriber(String subscriberId) {
        if (offsetMap.containsKey(subscriberId)) {
            throw new IllegalArgumentException("Subscriber already exists");
        }

        offsetMap.put(subscriberId, 0);
    }

    public void publish(String message) {
        dataStream.add(message);
    }

    public void ack(String subscriberId, int offset) {
        if (!offsetMap.containsKey(subscriberId)) {
            throw new IllegalArgumentException("Subscriber does not exist");
        }

        if (offsetMap.get(subscriberId) == offset) {
            offsetMap.put(subscriberId, offset + 1);
        }
    }

    public Response getMessage(String subscriberId) {
        if (!offsetMap.containsKey(subscriberId)) {
            throw new IllegalArgumentException("Subscriber does not exist");
        }
        int offset = offsetMap.get(subscriberId);
        if (offset < 0 || offset >= dataStream.size()) {
            return null;
        }
        Response response = new Response(dataStream.get(offset), offset);
        return response;
    }
}
