package com.eva.service.proxy;

import com.eva.core.utils.LocalCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 缓存代理类，便于缓存变更
 * @author Eva
 * @date 2019/3/23 20:31
 */
@Slf4j
@Component
public class CacheProxy<K,V> implements Cache<K, V> {

    // key前缀
    private String keyPrefix = "";

    // 默认超时时间(s)
    private long defaultExpireTime = 86400;

    @Autowired
    private LocalCache<K,V> localCache;

    public CacheProxy () {
        log.debug("CacheProxy: new, keyPrefix = [" + keyPrefix + "], defaultExpireTime = [" + defaultExpireTime + "]");
    }

    public CacheProxy (String keyPrefix, long defaultExpireTime) {
        log.trace("CacheProxy: new, keyPrefix = [" + keyPrefix + "], defaultExpireTime = [" + defaultExpireTime + "]");
        this.keyPrefix = keyPrefix;
        this.defaultExpireTime = defaultExpireTime;
    }

    @Override
    public V get(K key) throws CacheException {
        log.trace("CacheProxy: get, key = [" + key + "]");
        if (key == null) {
            return null;
        }
        return localCache.get(getKey(key));
    }

    @Override
    public V put(K key, V value) throws CacheException {
        log.trace("CacheProxy: put, key = [" + key + "]");
        if (key == null) {
            log.warn("CacheProxy: put, key can not be null");
        }
        localCache.set(getKey(key), value, defaultExpireTime * 1000);
        return value;
    }

    public V put(K key, V value, long expire) throws CacheException {
        log.trace("CacheProxy: put, key = [" + key + "]");
        if (key == null) {
            log.warn("CacheProxy: put, key can not be null");
        }
        localCache.set(getKey(key), value, expire * 1000);
        return value;
    }

    @Override
    public void clear() throws CacheException {
        log.trace("CacheProxy: clear");
        localCache.clear();
    }

    @Override
    public int size() {
        log.trace("CacheProxy: size");
        return localCache.size();
    }

    @Override
    public Set<K> keys() {
        log.trace("CacheProxy: keys");
        Set<K> keys = localCache.keys();
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        return keys;
    }

    public Set<K> keys(String keyPattern) {
        if (keyPattern == null || "".equals(keyPattern)) {
            return keys();
        }
        Set<K> keys = this.keys();
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        Set<K> filterKeys = new HashSet<>();
        Iterator<K> iter = keys.iterator();
        while(iter.hasNext()) {
            K key = iter.next();
            if (key instanceof String && ((String) key).matches(keyPattern)) {
                filterKeys.add(key);
            }
        }
        return filterKeys;
    }

    @Override
    public Collection<V> values() {
        log.trace("CacheProxy: values");
        Collection<V> values = localCache.values();
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyList();
        }
        return values;
    }

    @Override
    public V remove(K key) throws CacheException {
        log.trace("CacheProxy: remove, key = [" + key + "]");
        if (key == null) {
            return null;
        }
        return localCache.remove(getKey(key));
    }

    private K getKey (K key) {
        return (K)(key instanceof String ? this.keyPrefix + key : key);
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public long getDefaultExpireTime() {
        return defaultExpireTime;
    }

    public void setDefaultExpireTime(long defaultExpireTime) {
        this.defaultExpireTime = defaultExpireTime;
    }
}
