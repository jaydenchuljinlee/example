package com.ms.organization.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class KafkaProducer {
    private final StreamBridge streamBridge;

    @Async
    public void send(Object payload) {
        streamBridge.send(
                "producer-out-0",
                MessageBuilder.withPayload(payload)
                        .setHeader(
                                KafkaHeaders.MESSAGE_KEY,
                                UUID.randomUUID().toString()).build()
        );

    }
}
