package com.eva.service.proxy;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 缓存代理类，便于缓存变更
 * @author Eva.Caesar Liu
 * @date 2019/3/23 20:31
 */
@Scope(value = "prototype")
@Slf4j
@Component
public class CacheProxy<K,V> implements Cache<K, V> {

    // key前缀
    private String keyPrefix = "";

    @Autowired
    private RedisTemplate<K, V> redisTemplate;

    public CacheProxy () {
        log.debug("CacheProxy: new, keyPrefix = [" + keyPrefix + "]");
    }

    public CacheProxy (String keyPrefix) {
        log.trace("CacheProxy: new, keyPrefix = [" + keyPrefix + "]");
        this.keyPrefix = keyPrefix;
    }

    @Override
    public V get(K key) throws CacheException {
        log.trace("CacheProxy: get, key = [" + key + "]");
        if (key == null) {
            return null;
        }
        return redisTemplate.opsForValue().get(getKey(key));
    }

    @Override
    public V put(K key, V value) throws CacheException {
        log.trace("CacheProxy: put, key = [" + key + "]");
        if (key == null) {
            log.warn("CacheProxy: put, key can not be null");
        }
        redisTemplate.opsForValue().set(getKey(key), value);
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
        redisTemplate.opsForValue().set(this.getKey(key), value, expire, TimeUnit.MILLISECONDS);
        return value;
    }

    @Override
    public void clear() throws CacheException {
        log.trace("CacheProxy: clear");
        Set<K> keys = this.keys();
        redisTemplate.delete(keys);
    }

    @Override
    public int size() {
        log.trace("CacheProxy: size");
        return this.keys().size();
    }

    @Override
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

    @Override
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

    @Override
    public V remove(K key) throws CacheException {
        log.trace("CacheProxy: remove, key = [" + key + "]");
        if (key == null) {
            return null;
        }
        V value = this.get(getKey(key));
        redisTemplate.delete(getKey(key));
        return value;
    }

    private K getKey (K key) {
        return (key instanceof String ? (K)(this.keyPrefix + key) : key);
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
