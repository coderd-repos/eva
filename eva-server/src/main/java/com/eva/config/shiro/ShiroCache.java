package com.eva.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Shiro缓存
 * 技术参考：shiro-redis
 * @author Caesar Liu
 * @date 2021-06-05 12:59
 */
@Scope(value = "prototype")
@Slf4j
@Component
public class ShiroCache implements Cache<Object, Serializable> {

    private String keyPrefix = "";

    @Resource(name="sessionRedisTemplate")
    private RedisTemplate<Object, Serializable> redisTemplate;

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
        return redisTemplate.opsForValue().get(getKey(key));
    }

    @Override
    public Serializable put(Object key, Serializable value) throws CacheException {
        if (key == null) {
            return null;
        }
        redisTemplate.opsForValue().set(getKey(key), value);
        return value;
    }

    public Serializable put(Object key, Serializable value, int timeout) throws CacheException {
        if (key == null) {
            return null;
        }
        redisTemplate.opsForValue().set(getKey(key), value, timeout, TimeUnit.SECONDS);
        return value;
    }

    @Override
    public void clear() throws CacheException {
        Set<Object> keys = this.keys();
        redisTemplate.delete(keys);
    }

    @Override
    public int size() {
        return this.keys().size();
    }

    @Override
    public Set<Object> keys() {
        Set<Object> keys = redisTemplate.keys(keyPrefix + "*");
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
            values.add(redisTemplate.opsForValue().get(k));
        }
        return values;
    }

    @Override
    public Serializable remove(Object key) throws CacheException {
        if (key == null) {
            return null;
        }
        Serializable value = this.get(getKey(key));
        redisTemplate.delete(getKey(key));
        return value;
    }

    private Object getKey (Object key) {
        if (key instanceof PrincipalCollection) {
            return this.keyPrefix + getRedisKeyFromPrincipalIdField((PrincipalCollection)key);
        }
        return (key instanceof String ? (this.keyPrefix + key) : key);
    }

    /**
     * 获取redis cache key
     */
    private String getRedisKeyFromPrincipalIdField(PrincipalCollection key) {
        Object principalObject = key.getPrimaryPrincipal();
        if (principalObject instanceof String) {
            return principalObject.toString();
        } else {
            Method pincipalIdGetter = this.getPrincipalIdGetter(principalObject);
            return this.getIdObj(principalObject, pincipalIdGetter);
        }
    }

    private Method getPrincipalIdGetter(Object principalObject) {
        Method pincipalIdGetter;
        String principalIdMethodName = this.getPrincipalIdMethodName();

        try {
            pincipalIdGetter = principalObject.getClass().getMethod(principalIdMethodName);
            return pincipalIdGetter;
        } catch (NoSuchMethodException e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }

    private String getIdObj(Object principalObject, Method pincipalIdGetter) {
        try {
            Object idObj = pincipalIdGetter.invoke(principalObject);
            String redisKey = idObj.toString();
            return redisKey;
        } catch (Exception e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }

    private String getPrincipalIdMethodName() {
        return "getId";
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
