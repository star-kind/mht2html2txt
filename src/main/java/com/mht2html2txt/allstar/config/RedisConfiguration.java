package com.mht2html2txt.allstar.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;

/**
 * redis数据模版
 * 
 * @author admin
 *
 */
@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {
	/**
	 * 兼顾解决了springboot-redis乱码之患
	 * 
	 * @param factory
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);

		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		template.setKeySerializer(stringRedisSerializer);// 设置key采用String的序列化方式
		template.setHashKeySerializer(stringRedisSerializer);// 设置hash的key也采用String的序列化方式

		FastJsonRedisSerializer jsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
		jsonRedisSerializer.getFastJsonConfig().setSerializerFeatures(SerializerFeature.WriteClassName);
		ParserConfig.getGlobalInstance().addAccept("com.mht2html2txt.allstar.");

		template.setValueSerializer(jsonRedisSerializer); // 设置value采用的fastjson的序列化方式
		template.setHashValueSerializer(jsonRedisSerializer);// 设置hash的value采用的fastjson的序列化方式
		template.setDefaultSerializer(jsonRedisSerializer);// 设置其他默认的序列化方式为fastjson

		template.afterPropertiesSet();
		return template;
	}

}