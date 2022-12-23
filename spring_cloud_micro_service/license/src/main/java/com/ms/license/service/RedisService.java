package com.ms.license.service;

import com.ms.license.model.Organization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String ORGANIZATION = "organization";

    public void send(Organization org) {
        ZSetOperations<String, Object> orgZSetOperations = redisTemplate.opsForZSet();

        orgZSetOperations.add(org.getId(), org, LocalDateTime.now().getSecond());

        this.redisTemplate.convertAndSend(ORGANIZATION, org);
    }

    public Organization getOrgBy(String id) {
        return (Organization) redisTemplate.opsForValue().get(id);
    }
}
