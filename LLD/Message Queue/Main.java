import models.Subscriber;
import models.Topic;
import services.BrokerService;
import services.PublisherService;
import services.SubscriberService;

public class Main {
    public static void main(String[] args) {
        BrokerService brokerService = new BrokerService();
        PublisherService publisherService = new PublisherService(brokerService);
        SubscriberService subscriberService = new SubscriberService(brokerService);

        Topic topic = new Topic("orders");
        Subscriber subscriber = new Subscriber("order-processor");

        publisherService.createTopic(topic);
        subscriberService.subscribe(subscriber, topic);
        publisherService.publish(topic, "Order received");

        String message = subscriberService.consume(subscriber, topic);
        if (message != null) {
            System.out.println(message);
        }
    }
}
