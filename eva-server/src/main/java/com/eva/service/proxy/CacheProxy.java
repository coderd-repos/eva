package com.eva.service.proxy;

import com.eva.core.utils.LocalCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 缓存代理类，便于缓存变更
 * @author Caesar Liu
 * @date 2019/3/23 20:31
 */
@Slf4j
@Component
public class CacheProxy {

    /**
     * 设置缓存，默认过期时间为24h
     * @param key：缓存key
     * @param value：缓存内容
     * @author Caesar Liu
     * @date 2019/3/23 20:38
     */
    public void set(String key, Object value) {
        LocalCache.getInstance().set(key, value, 24 * 60 * 60 * 1000);
    }

    /**
     * 设置缓存
     * @param key：缓存key
     * @param value：缓存内容
     * @param timeout：缓存时间（毫秒）
     * @author Caesar Liu
     * @date 2019/3/23 20:38
     */
    public void set(String key, Object value, long timeout) {
        LocalCache.getInstance().set(key, value, timeout);
    }

    /**
     * 获取缓存内容
     * @param key：缓存key
     * @param <T>
     * @return
     * @author Caesar Liu
     * @date 2019/3/23 20:38
     */
    public <T> T get(String key) {
        return LocalCache.getInstance().get(key);
    }

    /**
     * 删除缓存
     * @param key：缓存key
     * @author Caesar Liu
     * @date 2019/3/23 20:38
     */
    public void remove(String key) {
        LocalCache.getInstance().remove(key);
    }

    /**
     * 重置缓存诞生时间（延长超时时间）
     * @param key: 缓存key
     * @author Caesar Liu
     * @date 2020-07-27 10:28
     */
    public void relive(String key) {
        LocalCache.getInstance().relive(key);
    }

}
