package com.ms.license.cloud.stream.binder.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.license.model.Organization;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class MessageDeserializer implements Deserializer<Organization> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Organization deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(new String(data), Organization.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}
