/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package kafka.basics;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Random;

public class ProducerDemo {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());

    public static void main(String[] args) {
        log.info("Producer Demo");

        // create Producer properties
        Properties properties = new Properties();

        // For connection to LocalHost
        // properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        // For connection to apache server on conduktor platform
        properties.setProperty("bootstrap.servers", "cluster.playground.cdkt.io:9092");
        properties.setProperty("security.protocol", "SASL_SSL");
        properties.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"5xBCPL0t6Mzl0UaqaR2vqb\" password=\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2F1dGguY29uZHVrdG9yLmlvIiwic291cmNlQXBwbGljYXRpb24iOiJhZG1pbiIsInVzZXJNYWlsIjpudWxsLCJwYXlsb2FkIjp7InZhbGlkRm9yVXNlcm5hbWUiOiI1eEJDUEwwdDZNemwwVWFxYVIydnFiIiwib3JnYW5pemF0aW9uSWQiOjcxNDIwLCJ1c2VySWQiOjgyNzg5LCJmb3JFeHBpcmF0aW9uQ2hlY2siOiI4MDFiYWViYy1iMjRkLTQzZWEtODcyOS1kYTdhNzU1OWZlZWMifX0.-FQ9bI1-PujXCEGgKZT_gz7znQ3wqJjtSkVTyxC2HvQ\";");
        properties.setProperty("sasl.mechanism", "PLAIN");

        // properties for sending a message

        // Set serialializers (This keys and values would be converted to binaries using this serializer)
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        //  Create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // create a producer record 
        // In constructor we can pass a topic, partition, timestamp, key, value, headers (In below statement, we are passing topic name, key and value).
        // Here by passing the key, the partition for that message in the topic is decided. We can also specifically specify the partition number
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("demo_java", "key1", "value1");

        // send the data to Kafka (This send() method is asynchronous)
        producer.send(producerRecord);

        // if we want to block till the producer sends all the data
        // producer.flush();

        // This calls producer.flash() and then closes the connection
        producer.close();

    }
}