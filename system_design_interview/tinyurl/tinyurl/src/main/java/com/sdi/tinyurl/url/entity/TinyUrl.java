package com.sdi.tinyurl.url.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@AllArgsConstructor
@RedisHash("tiny_url")
public class TinyUrl {
    @Id
    private String id;

    private Url url;
}
