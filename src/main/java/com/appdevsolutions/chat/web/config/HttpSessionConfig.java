package com.appdevsolutions.chat.web.config;

import org.springframework.context.annotation.Configuration;

@Configuration
/*@EnableRedisHttpSession(maxInactiveIntervalInSeconds=6000)*/
public class HttpSessionConfig {

	/*private static final Logger LOGGER=LoggerFactory.getLogger(HttpSessionConfig.class);
	//private static final String HTTP_SESSION_CONST_HEADER_NAME="ssotoken";
	
	@Bean
    public JedisConnectionFactory connectionFactory() throws IOException{
    	final JedisConnectionFactory jedisConnectionFactory= new JedisConnectionFactory(); 
    	LOGGER.debug("HttpSession jedisConnectionFactory bean has been created with default config : "+jedisConnectionFactory);
    	return jedisConnectionFactory;
    }
	
	@Bean
	HeaderHttpSessionStrategy getHeaderHttpSessionStrategy() {
		LOGGER.debug("HttpSession headerHttpSessionStrategy bean is being created");
		final HeaderHttpSessionStrategy headerHttpSessionStrategy=new HeaderHttpSessionStrategy();
		LOGGER.debug("HttpSession headerHttpSessionStrategy bean is being set HeaderName : "+HTTP_SESSION_CONST_HEADER_NAME);
		headerHttpSessionStrategy.setHeaderName(HTTP_SESSION_CONST_HEADER_NAME);
		LOGGER.debug("HttpSession headerHttpSessionStrategy bean has been created");
		return headerHttpSessionStrategy;
	}
	
	@Bean
	CookieHttpSessionStrategy getCookieHttpSessionStrategy(){
		final CookieHttpSessionStrategy cookieHttpSessionStrategy=new CookieHttpSessionStrategy();
		cookieHttpSessionStrategy.setCookieName("APP_DEV_SESSION");
		return cookieHttpSessionStrategy;
	}
	
	@Bean
	RedisHttpSessionConfiguration getRedisHttpSessionConfiguration() {
		LOGGER.debug("HttpSession redisHttpSessionConfiguration bean is being created");
		final RedisHttpSessionConfiguration redisHttpSessionConfiguration=new RedisHttpSessionConfiguration();
		HttpSessionStrategy httpSessionStrategy=getCookieHttpSessionStrategy();
		LOGGER.debug("HttpSession redisHttpSessionConfiguration bean is being set httpSessionStrategy : "+httpSessionStrategy);
		redisHttpSessionConfiguration.setHttpSessionStrategy(httpSessionStrategy);
		LOGGER.debug("HttpSession redisHttpSessionConfiguration bean has been created");
		return redisHttpSessionConfiguration;
	}*/
	
	/*@Bean
	public HttpSessionEventPublisher getHttpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}*/
}