package com.eva.config.shiro;

import com.eva.service.proxy.CacheProxy;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Shiro缓存
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Scope(value = "prototype")
@Slf4j
@Component
public class ShiroCache implements Cache<Object, Serializable> {

    private String keyPrefix = "";

    @Autowired
    private CacheProxy<Object, Serializable> cacheProxy;

    public ShiroCache () {
        log.debug("ShiroCache: new, keyPrefix = [" + keyPrefix + "]");
    }

    public ShiroCache(String keyPrefix) {
        log.debug("ShiroCache: new, keyPrefix = [" + keyPrefix + "]");
        this.keyPrefix = keyPrefix;
    }

    @Override
    public Serializable get(Object key) throws CacheException {
        if (key == null) {
            return null;
        }
        return cacheProxy.get(getKey(key));
    }

    @Override
    public Serializable put(Object key, Serializable value) throws CacheException {
        if (key == null) {
            return null;
        }
        cacheProxy.put(getKey(key), value);
        return value;
    }

    public Serializable put(Object key, Serializable value, int timeout) throws CacheException {
        if (key == null) {
            return null;
        }
        cacheProxy.put(getKey(key), value, timeout);
        return value;
    }

    @Override
    public void clear() throws CacheException {
        Set<Object> keys = this.keys();
        cacheProxy.remove(keys);
    }

    @Override
    public int size() {
        return this.keys().size();
    }

    @Override
    public Set<Object> keys() {
        Set<Object> keys = cacheProxy.keys(keyPrefix + "*");
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        return keys;
    }

    @Override
    public Collection<Serializable> values() {
        Collection<Serializable> values = new ArrayList<>();
        Set<Object> keys = this.keys();
        if (CollectionUtils.isEmpty(keys)) {
            return values;
        }
        for (Object k : keys) {
            values.add(cacheProxy.get(k));
        }
        return values;
    }

    @Override
    public Serializable remove(Object key) throws CacheException {
        if (key == null) {
            return null;
        }
        Serializable value = this.get(getKey(key));
        cacheProxy.remove(getKey(key));
        return value;
    }

    private Object getKey (Object key) {
        return (key instanceof String ? (this.keyPrefix + key) : key);
    }
}
