package com.ms.organization.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.organization.domain.Organization;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessageSerializer implements Serializer<Organization> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Organization data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch(Exception e) {
            throw new SerializationException(e);
        }

    }
}
