package com.eva.config.shiro;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.codec.Base64;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * Session序列化
 * 技术参考：Jackson2JsonRedisSerializer
 * @author Eva.Caesar Liu
 * @date 2021-06-05 00:05
 */
public class ShiroSessionSerializer implements RedisSerializer<Serializable> {

    @Override
    public byte[] serialize(Serializable obj) throws SerializationException {
        if (obj == null) {
            return new byte[0];
        }
        String sessionBase64 = Base64.encodeToString(SerializationUtils.serialize(obj));
        return sessionBase64.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Serializable deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        String sessionString = new String(bytes, StandardCharsets.UTF_8);
        byte[] sessionBytes = Base64.decode(sessionString);
        return SerializationUtils.deserialize(sessionBytes);
    }
}
