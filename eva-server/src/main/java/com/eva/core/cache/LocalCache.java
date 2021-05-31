package com.eva.core.cache;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单机本地缓存，项目初始阶段使用
 * - 超过指定大小可通过日志方式报警
 * - 延长超时时间
 * @author Eva.Caesar Liu
 * @date 2019/3/23 17:57
 */
@Slf4j
@Component
public final class LocalCache<K,V> {

    // 警告大小
    private final int WARN_SIZE = 1073741824;

    // GB大小
    private final int GB_SIZE = 1073741824;

    // 缓存实例
    private static LocalCache instance;

    // 数据存储对象
    private ConcurrentHashMap<K, Value<V>> cache = new ConcurrentHashMap();

    private LocalCache(){}

    public static LocalCache getInstance() {
        if(instance == null) {
            synchronized (LocalCache.class) {
                if(instance == null)
                    instance = new LocalCache();
            }
        }
        return instance;
    }

    /**
     * 添加缓存，超时时间为30分钟
     * @param key 缓存键
     * @param value 缓存值
     */
    public void put(K key, V value) {
        Value<V> v = cache.get(key);
        if (v != null) {
            v.value = value;
            return;
        }
        put(key, value, 30 * 60 * 1000);
    }

    /**
     * 添加缓存
     * @param key 缓存键
     * @param value 缓存值
     * @param timeout 超时时间
     */
    public void put(K key, V value, long timeout) {
        if(key == null) throw new NullPointerException("key can not be null");
        // 清理旧数据
        long now = System.currentTimeMillis();
        for(Map.Entry<K, Value<V>> entry: cache.entrySet()){
            Value v = entry.getValue();
            if(v.getValue() == null) // 值为空时清除掉
                cache.remove(entry.getKey());
            if(now - v.getBirthtime() > v.getTimeout()) // 超时时清除掉
                cache.remove(entry.getKey());
        }

        // 添加缓存
        Value<V> v = new Value<>();
        v.setBirthtime(now);
        v.setTimeout(timeout);
        v.setValue(value);
        cache.put(key, v);

        // 获取对象大小，超过一定大小打印警告
        int cacheSize = SerializationUtils.serialize(cache).length;
        if(cacheSize > WARN_SIZE) {
            log.warn("本地缓存已超过{}G，当前缓存容量为：{}G", (double) Math.round((double) WARN_SIZE / GB_SIZE * 100) / 100, (double) Math.round((double) cacheSize / GB_SIZE * 100) / 100);
        }
    }

    /**
     * 根据key获取缓存对象
     * @param key 缓存键
     */
    public V get(K key) {
        if(key == null) return null;
        Value<V> value = cache.get(key);
        if(value == null)
            return null;
        if(value.getValue() == null)
            return null;
        if(System.currentTimeMillis() - value.getBirthtime() > value.getTimeout()) {
            remove(key);
            return null;
        }
        return value.getValue();
    }

    /**
     * 根据key获取缓存对象
     * @param key 缓存键
     *
     * @return V
     */
    public V remove(K key) {
        if(key == null) return null;
        Value<V> value = cache.get(key);
        if (value == null) {
            return null;
        }
        cache.remove(key);
        return value.value;
    }

    /**
     * 清空缓存
     */
    public void clear () {
        cache.clear();
    }

    /**
     * 获取缓存数
     */
    public int size () {
        return cache.size();
    }

    /**
     * 获取缓存所有的key
     */
    public Set<K> keys () {
        return cache.keySet();
    }

    /**
     * 获取缓存值
     */
    public Collection<V> values () {
        Collection<Value<V>> values = cache.values();
        List<V> vs = new ArrayList(keys().size());
        for (Value<V> v : values) {
            if (v.value != null) {
                vs.add(v.value);
            }
        }
        return vs;
    }

    /**
     * 重置缓存诞生时间
     * @param key 缓存键
     */
    public void relive(String key) {
        if (key == null) return;
        Value v = cache.get(key);
        if (v == null) {
            return;
        }
        v.setBirthtime(new Date().getTime());
    }

    @Data
    private static class Value<V> implements Serializable {

        // 缓存具体值
        private V value;

        // 存储时间
        private long birthtime;

        // 超时时间
        private long timeout;
    }

}
