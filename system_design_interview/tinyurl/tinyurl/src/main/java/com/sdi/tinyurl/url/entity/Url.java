package com.sdi.tinyurl.url.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {
    @Id
    private String id;

    @Column(name = "short_url")
    private String shortUrl;
    @Column(name = "long_url")
    private String longUrl;

    private Url(String id, String shortUrl, String longUrl) {
        this.id = id;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

    public static Url of(String id, String shortUrl, String longUrl) {
        return new Url(id, shortUrl, longUrl);
    }
}
