package com.eva.config.shiro;

import com.eva.service.proxy.CacheProxy;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 自定义Shiro CacheManager，实现与代理缓存的结合
 * 技术参考：shiro-redis
 * @author Caesar Liu
 * @date 2021-05-28 01:01
 */
@Slf4j
@Component
public class ShiroCacheManager implements CacheManager {

    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap();

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        log.debug("get cache, name=" + name);
        Cache cache = this.caches.get(name);
        if (cache == null) {
            cache = new CacheProxy("shiro:cache:", 1800);
            this.caches.put(name, cache);
        }
        return cache;
    }
}
