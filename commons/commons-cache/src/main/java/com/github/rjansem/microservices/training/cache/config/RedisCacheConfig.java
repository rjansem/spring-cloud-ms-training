package com.github.rjansem.microservices.training.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.stream.Collectors;

/**
 * Configuration du cache Redis
 *
 * @author jntakpe
 */
@Configuration
@EnableCaching
@EnableRedisRepositories
public class RedisCacheConfig {

    private final RedisTemplate redisTemplate;

    private final RedisCacheProperties redisCacheProperties;

    @Autowired
    public RedisCacheConfig(RedisTemplate redisTemplate, RedisCacheProperties redisCacheProperties) {
        this.redisTemplate = redisTemplate;
        this.redisCacheProperties = redisCacheProperties;
    }

    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(3600);
        configureCaches(cacheManager);
        return cacheManager;
    }

    private RedisCacheManager configureCaches(RedisCacheManager cacheManager) {
        cacheManager.setCacheNames(redisCacheProperties.getCaches().stream()
                .map(RedisCacheProperties.RedisCache::getName)
                .collect(Collectors.toList()));
        cacheManager.setUsePrefix(true);
        cacheManager.setExpires(redisCacheProperties.getCaches().stream()
                .collect(Collectors.toMap(RedisCacheProperties.RedisCache::getName, RedisCacheProperties.RedisCache::getExpiry)));
        return cacheManager;
    }
}
