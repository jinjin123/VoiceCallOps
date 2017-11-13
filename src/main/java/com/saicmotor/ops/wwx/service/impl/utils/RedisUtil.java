package com.saicmotor.ops.wwx.service.impl.utils;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * Created by Shen_JM on 2017/11/2.
 */
public enum RedisUtil {
//	@Autowired
//	private Environment env;
//
//	@Bean(destroyMethod = "shutdown")
//	public RedissonClient redissonClient() throws IOException {
//		String[] profiles = env.getActiveProfiles();
//		String profile = "";
//		if(profiles.length > 0) {
//			profile = "-" + profiles[0];
//		}
//		return Redisson.create(
//				Config.fromYAML(new ClassPathResource("redisson" + profile + ".yml").getInputStream())
//		);
//	}
//
//	public static void main(String[] args) {
//	}
}
