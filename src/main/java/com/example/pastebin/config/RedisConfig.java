package com.example.pastebin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.net.URI;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        URI redisUri = URI.create(System.getenv("REDIS_URL"));

        RedisStandaloneConfiguration redisConfig =
                new RedisStandaloneConfiguration(redisUri.getHost(), redisUri.getPort());

        String userInfo = redisUri.getUserInfo(); // default:password
        if (userInfo != null && userInfo.contains(":")) {
            redisConfig.setPassword(RedisPassword.of(userInfo.split(":", 2)[1]));
        }

        LettuceClientConfiguration clientConfig =
                LettuceClientConfiguration.builder()
                        .useSsl()
                        .disablePeerVerification()
                        .build();

        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }
}
