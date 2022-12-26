package com.ms.license.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.license.annotation.ThirdPartyLog;
import com.ms.license.model.Organization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.sql.Array;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    private static final String ORGANIZATION = "organization";

    @ThirdPartyLog(name = {"redis", "send"})
    public void send(Organization org) {
        ZSetOperations<String, Object> orgZSetOperations = redisTemplate.opsForZSet();

        orgZSetOperations.add(org.getId(), org, LocalDateTime.now().getSecond());
    }

    @ThirdPartyLog(name = {"redis", "getOrgBy"})
    public Organization getOrgBy(String id) {
        ZSetOperations.TypedTuple<String> zSetOperations = stringRedisTemplate.opsForZSet().popMin(id);

        if (zSetOperations == null) {
            throw new InvalidParameterException(id + " is not exist in redis");
        }
        
        String redisValue = Objects.requireNonNull(zSetOperations).getValue();

        try {
            return objectMapper.readValue(redisValue, Organization.class);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
