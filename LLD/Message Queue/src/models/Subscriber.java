package models;

import services.MessageQueueService;
import utils.OutputPrinter;

import java.util.UUID;

public class Subscriber {
    String id;
    MessageQueueService queue;
    String topic;
    OutputPrinter outputPrinter;
    public Subscriber(MessageQueueService queue, String topic, OutputPrinter outputPrinter) {
        this.id = UUID.randomUUID().toString();
        this.queue = queue;
        this.topic = topic;
        this.outputPrinter = outputPrinter;
    }

    public String getId() {
        return id;
    }
    public String getTopic() {
        return topic;
    }

    // This method starts a new thread using an explicit Runnable class
    public void run() {
        Runnable task = new SubscriberTask();
        Thread thread = new Thread(task);
        thread.start();
    }

    // Inner class implementing Runnable for the subscriber's logic
    private class SubscriberTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Response response = queue.getMessage(topic, id);
                    outputPrinter.printWithNewLine("ID: " + id + " Response: " + response);
                    if (response != null) {
                        int offset = response.getOffset();
                        queue.ack(topic, id, offset);
                    }
                    // Introduce a sleep to simulate a delay between processing messages
                    Thread.sleep(1000); // Sleep for 1 second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore the interrupted status
                    outputPrinter.printWithNewLine("Thread interrupted for Subscriber ID: " + id);
                    break; // Exit the loop
                }
            }
        }
    }
}
