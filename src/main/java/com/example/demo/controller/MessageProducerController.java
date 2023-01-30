package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.service.MessageProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("messages")
@Profile("producer")
@Slf4j
public class MessageProducerController {
    private final MessageProducerService messageProducerService;

    public MessageProducerController(MessageProducerService messageProducerService) {
        this.messageProducerService = messageProducerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Message> sendMessage(@RequestBody Message message) {
        messageProducerService.sendMessage(message).subscribe();
        return Mono.just(message);
    }
}
