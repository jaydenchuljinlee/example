package com.sdi.tinyurl.url.controller;

import com.sdi.tinyurl.url.dto.UrlDto;
import com.sdi.tinyurl.url.service.TinyUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/shorten")
@RestController
public class UrlController {
    private final TinyUrlService tinyUrlService;

    @GetMapping("{shorten}")
    public ResponseEntity<UrlDto> get(@PathVariable String shorten) {
        UrlDto data = tinyUrlService.getUrl(shorten);

        return ResponseEntity.ok(data);
    }

    @PostMapping
    public ResponseEntity<UrlDto> save(@RequestParam String url) {
        UrlDto data = tinyUrlService.saveUrl(url);

        return ResponseEntity.ok(data);
    }
}
