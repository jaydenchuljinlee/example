package com.ms.organization.config;

import com.ms.organization.domain.Organization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducer {
    private final StreamBridge streamBridge;

    @Async
    public void send(Organization payload) {
        streamBridge.send(
                "process-out-0", payload);
    }

    @Bean
    public Consumer<String> receive() {
        log.debug("process-0");
        return System.out::println;
    }
}
