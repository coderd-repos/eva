package com.eva.service.proxy;

import com.eva.core.cache.LocalCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 缓存代理类，便于缓存变更
 * @author Eva.Caesar Liu
 * @date 2021/06/10 11:26
 */
@Slf4j
@Component
public class CacheProxy<K,V> {

    @Autowired
    private LocalCache<K,V> localCache;

    /**
     * 获取缓存值
     * @param key 缓存键
     *
     * @return V
     */
    public V get(K key) throws CacheException {
        log.trace("CacheProxy: get, key = [" + key + "]");
        if (key == null) {
            return null;
        }
        return localCache.get(key);
    }

    /**
     * 获取缓存值
     * @param key 缓存键
     * @param value 缓存值
     *
     * @return V
     */
    public V put(K key, V value) throws CacheException {
        log.trace("CacheProxy: put, key = [" + key + "]");
        if (key == null) {
            log.warn("CacheProxy: put, key can not be null");
        }
        localCache.put(key, value);
        return value;
    }

    /**
     * 指定过期时长
     * @param key 缓存键
     * @param value 缓存值
     * @param expire 过期时间(s)
     *
     * @return V
     */
    public V put(K key, V value, int expire) throws CacheException {
        log.trace("CacheProxy: put, key = [" + key + "]");
        if (key == null) {
            log.warn("CacheProxy: put, key can not be null");
        }
        localCache.put(key, value, expire * 1000);
        return value;
    }

    /**
     * 指定过期时长
     * @param key 缓存键
     * @param value 缓存值
     * @param expire 过期时间(ms)
     *
     * @return V
     */
    public V put(K key, V value, long expire) throws CacheException {
        log.trace("CacheProxy: put, key = [" + key + "]");
        if (key == null) {
            log.warn("CacheProxy: put, key can not be null");
        }
        localCache.put(key, value, expire);
        return value;
    }

    /**
     * 清理缓存
     */
    public void clear() throws CacheException {
        log.trace("CacheProxy: clear");
        localCache.clear();
    }

    /**
     * 获取缓存数
     *
     * @return int
     */
    public int size() {
        log.trace("CacheProxy: size");
        return localCache.size();
    }

    /**
     * 获取缓存key
     *
     * @return Set<K>
     */
    public Set<K> keys() {
        log.trace("CacheProxy: keys");
        Set<K> keys = localCache.keys();
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        return keys;
    }

    /**
     * 获取缓存key
     * @param keyPattern 缓存键正则
     *
     * @return Set<K>
     */
    public Set<K> keys(String keyPattern) {
        log.trace("CacheProxy: keys, keyPattern = [" + keyPattern + "]");
        if (keyPattern == null || "".equals(keyPattern) || "*".equals(keyPattern)) {
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

    /**
     * 获取所有缓存值
     *
     * @return Collection<V>
     */
    public Collection<V> values() {
        log.trace("CacheProxy: values");
        Collection<V> values = localCache.values();
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyList();
        }
        return values;
    }

    /**
     * 获取所有缓存值
     * @param key 缓存键
     *
     * @return Collection<V>
     */
    public V remove(K key) throws CacheException {
        log.trace("CacheProxy: remove, key = [" + key + "]");
        if (key == null) {
            return null;
        }
        return localCache.remove(key);
    }

}
