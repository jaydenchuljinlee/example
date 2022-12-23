package com.ms.license.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.license.model.Organization;
import com.ms.license.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumer {
    private final ObjectMapper objectMapper;
    private final RedisService redisService;

    @KafkaListener(id = "process", topics = "process-1")
    public void listener(String value) throws JsonProcessingException {
        Organization org = objectMapper.readValue(value, Organization.class);
        redisService.send(org);
    }
}
