package com.example.hearthstone.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class StartupCacheCleaner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(StartupCacheCleaner.class);

    private final CacheManager cacheManager;

    public StartupCacheCleaner(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void run(ApplicationArguments args) {
        clear("cards:list");
        clear("cards:detail");
        log.info("Card caches cleared on startup");
    }

    private void clear(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }
}
