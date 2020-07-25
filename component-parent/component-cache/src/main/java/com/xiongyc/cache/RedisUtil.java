package com.xiongyc.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings(value = {"unchecked","rawtypes"})
public class RedisUtil {

	@Autowired
	private RedisTemplate redisTemplate;

	public Long getSeq(String tableName, long l) {
		return redisTemplate.opsForValue().increment(tableName, l);
	}

	public void set(Object key, Object opsForValue, long timeout, TimeUnit unit) {
		redisTemplate.opsForValue().set(key, opsForValue, timeout, unit);
	}
	
	public void set(Object key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}
	
	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}
}
