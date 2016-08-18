package com.appdevsolutions.chat.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan("com.appdevsolutions.chat.web")
public class AppConfig {
	
	private static final String PATH_PREFIX="/WEB-INF/views/";
	private static final String PATH_SUFFIX=".jsp";
	private static final String MESSAGE_PATH_SOURCE_NAME="classpath:message";
	private static final Logger LOGGER=LoggerFactory.getLogger(AppConfig.class);
	
	@Bean(name = "viewResolver")
	public InternalResourceViewResolver getViewResolver() {
		LOGGER.debug("creating view resolver bean with prefix : "+PATH_PREFIX+" and suffix : "+PATH_SUFFIX);
	    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	    viewResolver.setPrefix(PATH_PREFIX);
	    viewResolver.setSuffix(PATH_SUFFIX);
	    return viewResolver;
	}
	
	@Bean(name="messageSource")
	public ReloadableResourceBundleMessageSource getReloadableResourceBundleMessageSource() {
		LOGGER.debug("creating messageSource bean with message path source name : "+MESSAGE_PATH_SOURCE_NAME);
		ReloadableResourceBundleMessageSource messageSource=new ReloadableResourceBundleMessageSource();
		messageSource.setBasename(MESSAGE_PATH_SOURCE_NAME);
		return messageSource;
	}
	
	@Bean(name="md5PasswordEncoder")
	public Md5PasswordEncoder getMd5PasswordEncoder() {
		LOGGER.debug("creating md5PasswordEncoder bean");
		return new Md5PasswordEncoder();
	}
	
	@Bean(name="multipartResolver")
	public CommonsMultipartResolver getCommonsMultipartResolver() {
		LOGGER.info("creating common multipart resolver bean");
		return new CommonsMultipartResolver();
	}
	
	
	
}