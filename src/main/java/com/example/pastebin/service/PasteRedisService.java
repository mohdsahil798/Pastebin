package com.example.pastebin.service;

import com.example.pastebin.exception.NotFoundException;
import com.example.pastebin.model.Paste;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class PasteRedisService {

    private final StringRedisTemplate redisTemplate;

    private static final String PREFIX = "paste:";

    public void savePaste(
            String id,
            String content,
            Long expiresAt,
            Integer maxViews,
            Duration ttl
    ) {
        String key = PREFIX + id;

        redisTemplate.opsForHash().put(key, "content", content);

        if (expiresAt != null)
            redisTemplate.opsForHash().put(key, "expiresAt", expiresAt.toString());

        if (maxViews != null)
            redisTemplate.opsForHash().put(key, "views", maxViews.toString());

        if (ttl != null)
            redisTemplate.expire(key, ttl);
    }

    public Paste fetchPaste(String id, long now) {
        String key = PREFIX + id;

        if (!Boolean.TRUE.equals(redisTemplate.hasKey(key)))
            throw new NotFoundException("Paste not found");

        var data = redisTemplate.opsForHash().entries(key);

        String content = (String) data.get("content");

        Long expiresAt = data.containsKey("expiresAt")
                ? Long.parseLong((String) data.get("expiresAt"))
                : null;

        if (expiresAt != null && now > expiresAt) {
            redisTemplate.delete(key);
            throw new NotFoundException("Paste expired");
        }

        Integer views = data.containsKey("views")
                ? Integer.parseInt((String) data.get("views"))
                : null;

        if (views != null) {
            if (views <= 0) {
                redisTemplate.delete(key);
                throw new NotFoundException("View limit exceeded");
            }
            redisTemplate.opsForHash().put(key, "views", String.valueOf(views - 1));
        }

        return new Paste(content, expiresAt, views != null ? views - 1 : null);
    }
}

