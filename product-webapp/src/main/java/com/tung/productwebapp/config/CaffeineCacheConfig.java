package com.tung.productwebapp.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
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
    public CacheManager categoryCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("CATEGORY");
        cacheManager.setAllowNullValues(false);
        cacheManager.setCaffeine(categoryCaffeineCacheBuilder());
        return  cacheManager;
    }

    @Bean
    public CacheManager productCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("PRODUCT");
        cacheManager.setAllowNullValues(false);
        cacheManager.setCaffeine(searchCaffeineCacheBuilder());
        return  cacheManager;
    }

    private Caffeine<Object, Object> categoryCaffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .expireAfterAccess(60, TimeUnit.SECONDS);
    }

    private Caffeine<Object, Object> searchCaffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .weakKeys()
                .weakValues()
                .expireAfterAccess(60, TimeUnit.SECONDS);
    }
}
