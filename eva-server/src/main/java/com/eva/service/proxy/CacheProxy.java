package com.eva.service.proxy;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 缓存代理类，便于缓存变更
 * @author Eva.Caesar Liu
 * @date 2019/3/23 20:31
 */
@Slf4j
@Component
public class CacheProxy<K,V> {

    @Autowired
    private RedisTemplate<K, V> redisTemplate;

    public V get(K key) throws CacheException {
        log.trace("CacheProxy: get, key = [" + key + "]");
        if (key == null) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    public V put(K key, V value) throws CacheException {
        log.trace("CacheProxy: put, key = [" + key + "]");
        if (key == null) {
            log.warn("CacheProxy: put, key can not be null");
        }
        redisTemplate.opsForValue().set(key, value);
        return value;
    }

    /**
     * 指定过期时长
     * @param key 缓存键
     * @param value 缓存值
     * @param expire 过期时间(s)
     */
    public V put(K key, V value, int expire) throws CacheException {
        this.put(key, value, expire * 1000L);
        return value;
    }

    /**
     * 指定过期时长
     * @param key 缓存键
     * @param value 缓存值
     * @param expire 过期时间(ms)
     */
    public V put(K key, V value, long expire) throws CacheException {
        log.trace("CacheProxy: put, key = [" + key + "]");
        if (key == null) {
            log.warn("CacheProxy: put, key can not be null");
        }
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.MILLISECONDS);
        return value;
    }

    public void clear() throws CacheException {
        log.trace("CacheProxy: clear");
        Set<K> keys = this.keys();
        redisTemplate.delete(keys);
    }

    public int size() {
        log.trace("CacheProxy: size");
        return this.keys().size();
    }

    public Set<K> keys() {
        log.trace("CacheProxy: keys");
        Set<K> keys = redisTemplate.keys(null);
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        return keys;
    }

    public Set<K> keys(String keyPattern) {
        return redisTemplate.keys((K)keyPattern);
    }

    public Collection<V> values() {
        log.trace("CacheProxy: values");
        Collection<V> values = new ArrayList<>();
        Set<K> keys = this.keys();
        if (CollectionUtils.isEmpty(keys)) {
            return values;
        }
        for (K k : keys) {
            values.add(redisTemplate.opsForValue().get(k));
        }
        return values;
    }

    public V remove(K key) throws CacheException {
        log.trace("CacheProxy: remove, key = [" + key + "]");
        if (key == null) {
            return null;
        }
        V value = this.get(key);
        redisTemplate.delete(key);
        return value;
    }
}
