package com.eva.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 自定义Shiro CacheManager
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Slf4j
@Component
public class ShiroCacheManager implements CacheManager {

    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap();

    private static ApplicationContext applicationContext;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        log.debug("get cache, name=" + name);
        Cache cache = this.caches.get(name);
        if (cache == null) {
            cache = applicationContext.getBean(ShiroCache.class, "shiro:cache:");
            this.caches.put(name, cache);
        }
        return cache;
    }

    @Autowired
    public void setApplicationContext (ApplicationContext applicationContext) {
        if (ShiroCacheManager.applicationContext == null) {
            ShiroCacheManager.applicationContext = applicationContext;
        }
    }
}
