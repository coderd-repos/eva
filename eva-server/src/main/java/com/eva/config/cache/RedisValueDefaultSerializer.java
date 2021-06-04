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
 * Redis 值序列化
 * 技术参考：Jackson2JsonRedisSerializer
 * @author Eva.Caesar Liu
 * @date 2021-06-04 16:27
 */
class RedisValueDefaultSerializer<T> implements RedisSerializer<T> {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final ParserConfig parserConfig = new ParserConfig();

    {
        parserConfig.setAutoTypeSupport(true);
        JSON.DEFAULT_GENERATE_FEATURE = SerializerFeature.config(
                JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.SkipTransientField, false);
    }

    private final Class<T> type;

    RedisValueDefaultSerializer(Class<T> type) {
        this.type = type;
    }

    public T deserialize(@Nullable byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return JSON.parseObject(new String(bytes, DEFAULT_CHARSET), type, parserConfig);
    }

    public byte[] serialize(@Nullable Object v) throws SerializationException {
        if (v == null) {
            return new byte[0];
        }
        return JSON.toJSONBytes(v, SerializerFeature.WriteClassName);
    }

}
