# Kafka Overview (Go through the slides first!)

When a producer connects to any of the brokers, it gets the metadata which consists of
- List of brokers.
- Topics, partitions and info about which partition is managed by which broker (The leader broker for each partition). 
- The info about brokers which handle the replicas of the partition.

Each broker has the metadata.

## How is the metadata managed

- In older versions of kafka, <b>Zookeeper</b> was used to manage metadata, but now <b>Kafka controller</b> manages this metadata.
- Each broker sends heartbeat to the controller. If a broker does not send a heartbeat, then the controller would consider it dead and update the metadata. Brokers periodically synchronise the metadata internally and with the controller / zookeeper.
- The producer regularly fetches the metadata to update the list of brokers for each partition. The refresh would also be triggered in case of an error while publishing the message.