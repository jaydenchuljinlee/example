package com.sdi.tinyurl.url.dto;

import com.sdi.tinyurl.url.entity.Url;
import lombok.Data;

@Data
public class UrlDto {
    private String id;
    private String shortUrl;
    private String longUrl;

    private UrlDto() {}

    private UrlDto(Url entity) {
        this.id = entity.getId();
        this.shortUrl = entity.getShortUrl();
        this.longUrl = entity.getLongUrl();
    }

    public static UrlDto from(Url entity) {
        return new UrlDto(entity);
    }
}
