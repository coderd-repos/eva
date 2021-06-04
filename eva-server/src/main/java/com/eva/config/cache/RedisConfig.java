package com.eva.config.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * Redis配置
 * @author Eva.Caesar Liu
 * @date 2021-06-04 22:05
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Serializable, Serializable> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Serializable, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        RedisValueDefaultSerializer<Object> serializer = new RedisValueDefaultSerializer<>(Object.class);
        // 默认序列化方式
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        // 值序列化方式
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
