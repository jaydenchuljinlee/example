package com.sdi.tinyurl.url.repository;

import com.sdi.tinyurl.url.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {
    Url findByShortUrl(String shortUrl);
}
