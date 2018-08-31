package com.continuation.manager.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author tangxu
 * @date 2018/8/1318:51
 */
@Slf4j
@Service
@AllArgsConstructor
public class RedisService {

    public static final String VERIFY_HEADER = "VERIFY_";
    public static final String TOKEN_HEADER = "TOKEN_";
    public static final String TEST_PAPER_HEADER = "TEST_PAPER_";

    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    public void del(final String key){
        redisTemplate.delete(key);
    }

    /**
     * 获取缓存并重新设置保存时间
     *
     * @param key      键
     * @param timeLive 保存时间
     * @param cls      值类型
     * @param <T>      值对象
     * @return 缓存对象
     */
    public <T> T getSet(final String key, long timeLive, Class<T> cls) {
        T result = get(key, cls, true);
        if (null == result) {
            return null;
        }
        redisTemplate.expire(key, timeLive, TimeUnit.SECONDS);
        return result;
    }

    /**
     * 获取缓存
     *
     * @param key    键
     * @param clazz  获取对象类型
     * @param retain 是否保留
     * @param <T>    保存对象
     * @return 值
     */
    public <T> T get(String key, Class<T> clazz, boolean retain) {
        Object obj = redisTemplate.boundValueOps(key).get();
        if (!retain) {
            redisTemplate.delete(key);
        }
        return clazz.cast(obj);
    }

    /**
     * 将value对象写入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  保存时间
     */
    public void set(String key, Object value, long time) {
        if (value instanceof String) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value instanceof Integer) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value instanceof Double) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value instanceof Float) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value instanceof Short) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value instanceof Long) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value instanceof Boolean) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

}
