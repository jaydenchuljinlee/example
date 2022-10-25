package com.sdi.tinyurl.url.service;

import com.sdi.tinyurl.url.dto.UrlDto;
import com.sdi.tinyurl.url.entity.Url;
import com.sdi.tinyurl.url.repository.UrlRepository;
import de.huxhorn.sulky.ulid.ULID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Base64;

@RequiredArgsConstructor
@Service
public class TinyUrlServiceImpl implements TinyUrlService{
    private final UrlRepository repository;
    private final RedisTemplate redisTemplate;

    @Override
    public UrlDto saveUrl(String url) {
        ULID ulid = new ULID();
        String id = ulid.nextValue().toString();

        String shortedUrl = Base64.getEncoder().encodeToString(id.getBytes());

        Url entity = Url.of(id, shortedUrl, url);

        repository.save(entity);

        return UrlDto.from(entity);
    }

    @Cacheable(key = "#shortUrl", value = "UrlDto")
    @Override
    public UrlDto getUrl(String shortUrl) {
        Url entity = repository.findByShortUrl(shortUrl);

        return UrlDto.from(entity);
    }
}
