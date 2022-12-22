package com.ms.license.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {
    @KafkaListener(id = "process", topics = "process-1")
    public void listener(String key) {
        log.debug(key);
    }
}
