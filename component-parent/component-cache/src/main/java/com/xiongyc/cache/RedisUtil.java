package com.xiongyc.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@SuppressWarnings("rawtypes")
@Component
public class RedisUtil {

	@Autowired
	private RedisTemplate redisTemplate;

	@SuppressWarnings("unchecked")
	public Long getSeq(String tableName, long l) {
		return redisTemplate.opsForValue().increment(tableName, l);
	}

}
