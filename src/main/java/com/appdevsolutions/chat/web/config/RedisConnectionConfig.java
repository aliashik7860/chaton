package com.appdevsolutions.chat.web.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConnectionConfig {
	
	//private static final Logger LOGGER=LoggerFactory.getLogger(RedisConnectionConfig.class);
	
	@Bean
    public JedisConnectionFactory connectionFactory() throws IOException{
    	final JedisConnectionFactory jedisConnectionFactory= new JedisConnectionFactory(); 
    	//LOGGER.debug("HttpSession jedisConnectionFactory bean has been created with default config : "+jedisConnectionFactory);
    	return jedisConnectionFactory;
    }
	
	@Bean(name="redisTemplate")
	public RedisTemplate<String, String> getRedisTemplate() throws IOException{
		final RedisTemplate<String, String> redisTemplate=new RedisTemplate<String,String>();
		redisTemplate.setConnectionFactory(connectionFactory());
		redisTemplate.setKeySerializer( new StringRedisSerializer() );
		redisTemplate.setHashValueSerializer( new GenericToStringSerializer< Integer >( Integer.class ) );
		redisTemplate.setValueSerializer( new GenericToStringSerializer< Integer >( Integer.class ) );
		return redisTemplate;
	}
}