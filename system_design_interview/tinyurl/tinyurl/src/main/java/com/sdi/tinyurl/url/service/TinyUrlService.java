package com.sdi.tinyurl.url.service;

import com.sdi.tinyurl.url.dto.UrlDto;

public interface TinyUrlService {
    UrlDto saveUrl(String url);

    UrlDto getUrl(String longUrl);
}
