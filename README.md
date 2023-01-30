# Kafka with Java
Quick project to use Kafka, Spring and reactive APIs to send and receive messages.

SpringBoot Reactive project with both producer and consumers. Separated by profiles:
- producer
- consumer

## Getting started
Start required Kafka Service locally using `docker compose`
```bash
docker compose up -d
```

Create a topic (e.g. my-topic) with N (e.g. 2) partitions:
```bash
kafka-topics --bootstrap-server broker:9092 \
             --create \
             --topic my-topic \
             --replication-factor 1 \
             --partitions 2
```

When starting producer supply these command line args:
```bash
-Dspring.profiles.active=producer -Dserver.port=8080
```

When starting a consumer supply these command line args (can start multiple consumers on different ports:
```bash
-Dspring.profiles.active=consumer -Dserver.port=8081
```

## Links
- https://medium.com/event-driven-utopia/understanding-kafka-topic-partitions-ae40f80552e8
- https://medium.com/@TimvanBaarsen/apache-kafka-cli-commands-cheat-sheet-a6f06eac01b
- https://github.com/Kevded/example-reactive-spring-kafka-consumer-and-producer

### Useful commands:

Create a topic:
```bash
docker exec broker \
kafka-topics --bootstrap-server broker:9092 \
             --create \
             --topic my-topic \
             --replication-factor 1 \
             --partitions 2
```

Write a message to the topic:
```bash
docker exec --interactive --tty broker \
kafka-console-producer --bootstrap-server broker:9092 \
                       --topic my-topic
```

Read messages from the topic:
```bash
docker exec --interactive --tty broker \
kafka-console-consumer --bootstrap-server broker:9092 \
                       --topic my-topic \
                       --from-beginning
```

List topics:
```bash
docker exec --interactive --tty broker \
kafka-topics --bootstrap-server broker:9092 --list
```

Describe a topic
```bash
docker exec --interactive --tty broker \
kafka-topics --bootstrap-server broker:9092 --topic my-topic --describe
```