package com.example.demo.service;

import com.example.demo.model.Message;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Profile("consumer")
@Slf4j
public class MessageConsumerService {
    private final ReactiveKafkaConsumerTemplate<String, Message> kafkaConsumerTemplate;

    public MessageConsumerService(ReactiveKafkaConsumerTemplate<String, Message> kafkaConsumerTemplate) {
        this.kafkaConsumerTemplate = kafkaConsumerTemplate;
    }

    private Flux<Message> consumeMessages() {
        return kafkaConsumerTemplate
                .receiveAutoAck()
                // .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
                .doOnNext(consumerRecord -> log.info(
                                "received key={}, value={} from topic={}, partition={}, offset={}",
                                consumerRecord.key(),
                                consumerRecord.value(),
                                consumerRecord.topic(),
                                consumerRecord.partition(),
                                consumerRecord.offset()
                        )
                )
                .map(ConsumerRecord::value)
                .doOnNext(fakeConsumerDTO -> log.info("successfully consumed {}={}", Message.class.getSimpleName(), fakeConsumerDTO))
                .doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()));
    }

    @PostConstruct
    public void startMessageConsumption() {
        consumeMessages().subscribe();
    }
}
