package com.cloud.provider.utils;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;

/**
 * Redis帮助类  与 RedisPool 同时使用
 */
public final class RedisUtil {

	public static Object get(String key) {
		Jedis jedis = RedisPool.getJedis();
		try {
			String content = jedis.get(key);
			return content;
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		} finally {
			RedisPool.returnResource(jedis);
		}
		return null;
	}

	public static void set(String key, Object value) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.set(key, JSON.toJSONString(value));
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		} finally {
			RedisPool.returnResource(jedis);
		}
	}

	public static void set(String key, int seconds, Object value) {
		Jedis jedis = RedisPool.getJedis();
		try {
			jedis.setex(key, seconds, JSON.toJSONString(value));
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		} finally {
			RedisPool.returnResource(jedis);
		}
	}

	public static Boolean exists(String key) {
		Jedis jedis = RedisPool.getJedis();
		try {
			Boolean flag = jedis.exists(key);
			return flag;
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		} finally {
			RedisPool.returnResource(jedis);
		}
		return false;
	}
}
