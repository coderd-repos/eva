package com.eva.service.proxy;

import com.eva.core.utils.LocalCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

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

    // 默认超时时间
    private long defaultExpireTime = 86400;

    @Autowired
    private LocalCache<K,V> localCache;

    public CacheProxy () {}

    public CacheProxy (String keyPrefix, long defaultExpireTime) {
        log.debug("CacheProxy: new, keyPrefix = [" + keyPrefix + "], defaultExpireTime = [" + defaultExpireTime + "]");
        this.keyPrefix = keyPrefix;
        this.defaultExpireTime = defaultExpireTime;
    }

    @Override
    public V get(K key) throws CacheException {
        log.debug("CacheProxy: get, key = [" + key + "]");
        return localCache.get(getKey(key));
    }

    @Override
    public V put(K key, V value) throws CacheException {
        log.debug("CacheProxy: put, key = [" + key + "]");
        localCache.set(getKey(key), value, defaultExpireTime * 1000);
        return value;
    }

    public V put(K key, V value, long expire) throws CacheException {
        log.debug("CacheProxy: put, key = [" + key + "]");
        localCache.set(getKey(key), value, expire * 1000);
        return value;
    }

    @Override
    public void clear() throws CacheException {
        log.debug("CacheProxy: clear");
        localCache.clear();
    }

    @Override
    public int size() {
        log.debug("CacheProxy: size");
        return localCache.size();
    }

    @Override
    public Set<K> keys() {
        log.debug("CacheProxy: keys");
        Set<K> keys = localCache.keys();
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        log.debug("CacheProxy: values");
        Collection<V> values = localCache.values();
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyList();
        }
        return values;
    }

    @Override
    public V remove(K key) throws CacheException {
        log.debug("CacheProxy: remove, key = [" + key + "]");
        if (key == null) {
            return null;
        }
        return localCache.remove(getKey(key));
    }

    private K getKey (K key) {
        return (K)(key instanceof String ? this.keyPrefix + key : key);
    }
}
