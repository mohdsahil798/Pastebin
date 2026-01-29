package com.example.pastebin.controller;

import com.example.pastebin.exception.BadRequestException;
import com.example.pastebin.model.Paste;
import com.example.pastebin.service.PasteRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PasteApiController {

    private final PasteRedisService redisService;

    @GetMapping("/health")
    public Map<String, Boolean> health() {
        return Map.of("ok", true);
    }

    @PostMapping("/pastes")
    public Map<String, String> createPaste(
            @RequestParam String content,
            @RequestParam(required = false) Integer ttl_seconds,
            @RequestParam(required = false) Integer max_views
    ) {

        if (content == null || content.trim().isEmpty()) {
            throw new BadRequestException("Content is required");
        }

        String id = UUID.randomUUID().toString().substring(0, 8);
        long now = System.currentTimeMillis();

        Long expiresAt = ttl_seconds != null ? now + ttl_seconds * 1000L : null;
        Duration ttl = ttl_seconds != null ? Duration.ofSeconds(ttl_seconds) : null;

        redisService.savePaste(id, content, expiresAt, max_views, ttl);
        String baseUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build()
                .toUriString();

        return Map.of(
                "id", id,
                "url", baseUrl + "/p/" + id
        );
    }


    @GetMapping("/pastes/{id}")
    public Paste fetch(
            @PathVariable String id,
            @RequestHeader(value = "x-test-now-ms", required = false) Long testNow
    ) {
        long now = testNow != null ? testNow : System.currentTimeMillis();
        return redisService.fetchPaste(id, now);
    }
}








