package com.eva.config.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Redis 值序列化器
 * 技术参考：Jackson2JsonRedisSerializer
 * @author Eva.Caesar Liu
 * @date 2021-06-04 16:27
 */
class RedisValueDefaultSerializer<T> implements RedisSerializer<T> {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final ParserConfig parserConfig = new ParserConfig();

    {
        parserConfig.setAutoTypeSupport(true);
    }

    private final Class<T> type;

    RedisValueDefaultSerializer(Class<T> type) {
        this.type = type;
    }

    public T deserialize(@Nullable byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return JSON.parseObject(new String(bytes, DEFAULT_CHARSET), type, parserConfig);
        } catch (Exception e) {
            throw new SerializationException("Could not serialize value of redis: " + e.getMessage(), e);
        }
    }

    public byte[] serialize(@Nullable Object value) throws SerializationException {
        if (value == null) {
            return new byte[0];
        }
        try {
            return JSON.toJSONBytes(value, SerializerFeature.WriteClassName, SerializerFeature.PrettyFormat);
        } catch (Exception e) {
            throw new SerializationException("Could not serialize value of redis: " + e.getMessage(), e);
        }
    }

}
