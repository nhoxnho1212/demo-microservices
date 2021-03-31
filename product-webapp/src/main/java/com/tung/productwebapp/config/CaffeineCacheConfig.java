package com.tung.productwebapp.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineCacheConfig {

    @Bean
    @Primary
    public CacheManager CategoryCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("CATEGORY");
        cacheManager.setAllowNullValues(false);
        cacheManager.setCaffeine(CategoryCaffeineCacheBuilder());
        return  cacheManager;
    }

    @Bean
    public CacheManager SearchCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("SEARCH");
        cacheManager.setAllowNullValues(false);
        cacheManager.setCaffeine(SearchCaffeineCacheBuilder());
        return  cacheManager;
    }

    private Caffeine<Object, Object> CategoryCaffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .expireAfterAccess(60, TimeUnit.SECONDS);
    }

    private Caffeine<Object, Object> SearchCaffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .maximumSize(1000000000)
                .expireAfterAccess(60, TimeUnit.SECONDS);
    }
}
