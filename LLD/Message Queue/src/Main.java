import services.MessageQueueService;
import models.Subscriber;
import utils.OutputPrinter;

public class Main {
    public static void main(String[] args) {
        MessageQueueService messageQueueService = new MessageQueueService();
        OutputPrinter outputPrinter = new OutputPrinter();
        messageQueueService.createTopic("topic1");
        messageQueueService.createTopic("topic2");
        messageQueueService.createTopic("topic3");


        Subscriber subscriber1 = new Subscriber(messageQueueService, "topic1", outputPrinter);
        Subscriber subscriber2 = new Subscriber(messageQueueService, "topic1", outputPrinter);
        Subscriber subscriber3 = new Subscriber(messageQueueService, "topic2", outputPrinter);

        messageQueueService.addSubscriber("topic1", subscriber1.getId());
        messageQueueService.addSubscriber("topic1", subscriber2.getId());
        messageQueueService.addSubscriber("topic2", subscriber3.getId());


        messageQueueService.publish("topic1", "message1 from topic 1");
        messageQueueService.publish("topic1", "message2 from topic 1");
        messageQueueService.publish("topic2", "message1 from topic 2");

        subscriber1.run();
        subscriber2.run();
        subscriber3.run();
    }
}
