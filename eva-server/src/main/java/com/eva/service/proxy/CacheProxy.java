package com.eva.service.proxy;

import com.eva.core.utils.LocalCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
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
public class CacheProxy implements Cache<String, Serializable> {

    // key前缀
    private String keyPrefix = "";

    // 默认超时时间
    private long defaultExpireTime = 86400;

    public CacheProxy () {}

    public CacheProxy (String keyPrefix, long defaultExpireTime) {
        log.debug("CacheProxy: new, keyPrefix = [" + keyPrefix + "], defaultExpireTime = [" + defaultExpireTime + "]");
        this.keyPrefix = keyPrefix;
        this.defaultExpireTime = defaultExpireTime;
    }

    @Override
    public Serializable get(String key) throws CacheException {
        log.debug("CacheProxy: get, key = [" + key + "]");
        return LocalCache.getInstance().get(getKey(key));
    }

    @Override
    public Serializable put(String key, Serializable value) throws CacheException {
        log.debug("CacheProxy: put, key = [" + key + "]");
        LocalCache.getInstance().set(getKey(key), value, defaultExpireTime * 1000);
        return value;
    }

    @Override
    public void clear() throws CacheException {
        log.debug("CacheProxy: clear");
        LocalCache.getInstance().clear();
    }

    @Override
    public int size() {
        log.debug("CacheProxy: size");
        return LocalCache.getInstance().size();
    }

    @Override
    public Set<String> keys() {
        log.debug("CacheProxy: keys");
        Set<String> keys = LocalCache.getInstance().keys();
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        return keys;
    }

    @Override
    public Collection<Serializable> values() {
        log.debug("CacheProxy: values");
        Collection<Serializable> values = LocalCache.getInstance().values();
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyList();
        }
        return values;
    }

    @Override
    public Serializable remove(String key) throws CacheException {
        log.debug("CacheProxy: remove, key = [" + key + "]");
        if (key == null) {
            return null;
        }
        return LocalCache.getInstance().remove(getKey(key));
    }

    private String getKey (String key) {
        return this.keyPrefix + key;
    }
}
