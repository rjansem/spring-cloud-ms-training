package com.github.rjansem.microservices.training.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Propriétés liées aux caches
 *
 * @author jntakpe
 */
@Component
@ConfigurationProperties("redis")
public class RedisCacheProperties {

    private List<RedisCache> caches = new ArrayList<>();

    public List<RedisCache> getCaches() {
        return caches;
    }

    public void setCaches(List<RedisCache> caches) {
        this.caches = caches;
    }

    public static class RedisCache {

        private String name;

        private long expiry;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getExpiry() {
            return expiry;
        }

        public void setExpiry(long expiry) {
            this.expiry = expiry;
        }
    }

}
