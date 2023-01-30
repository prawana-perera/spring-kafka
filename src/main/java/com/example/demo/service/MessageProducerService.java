package com.example.demo.service;

import com.example.demo.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

import java.util.Random;

@Service
@Profile("producer")
@Slf4j
public class MessageProducerService {
    private final ReactiveKafkaProducerTemplate<String, Message> kafkaProducerTemplate;

    public MessageProducerService(ReactiveKafkaProducerTemplate<String, Message> kafkaProducerTemplate) {
        this.kafkaProducerTemplate = kafkaProducerTemplate;
    }

    public Mono<SenderResult<Void>> sendMessage(Message message) {
        Random random = new Random();
        String partition = String.valueOf(random.nextInt(2));

        return kafkaProducerTemplate
                .send("my-topic", partition, message)
                .doOnSuccess(senderResult ->
                        log.info(
                                "sent {} to topic {} partition {} offset : {}",
                                message,
                                senderResult.recordMetadata().topic(),
                                senderResult.recordMetadata().partition(),
                                senderResult.recordMetadata().offset()
                        )
                );
    }
}
