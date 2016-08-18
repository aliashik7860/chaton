package com.appdevsolutions.chat.web.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringWebAppInitializer implements WebApplicationInitializer{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(SpringWebAppInitializer.class);
	
	private static final String DISPATCHER_SERVLET_NAME="DispatcherServlet";
	private static final String SPRING_CONFIG_PACKAGE_LOCATION="com.appdevsolutions.chat.web.config";
	private static final String BASIC_AUTHENTICATION_FILTER_NAME="basicAuthenticationFilter";
	/*@Autowired
	ChatOnActiveUsersSessionListener chatOnActiveUsersSessionListener;*/
	
	/*@Autowired
	HttpSessionEventPublisher httpSessionEventPublisher;*/
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		WebApplicationContext context = getContext();
		servletContext.addFilter(BASIC_AUTHENTICATION_FILTER_NAME, new BasicAuthenticationFilter());
		//servletContext.addListener(getChatOnActiveUsersSessionListener());
        servletContext.addListener(new ContextLoaderListener(context));
        servletContext.addListener(new HttpSessionEventPublisher());
        //servletContext.addListener(new ChatOnActiveUsersSessionListener());
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        LOGGER.info("Context is loaded");
	}
	
	private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(SPRING_CONFIG_PACKAGE_LOCATION);
        return context;
    }
	/*private WebApplicationContext getWebApplicationContext() {
		WebApplicationContext context=new AnnotationConfigWebApplicationContext();
		
		return context;
	}*/
	/*@Bean
	public ChatOnActiveUsersSessionListener getChatOnActiveUsersSessionListener() {
		return new ChatOnActiveUsersSessionListener();
	}*/
	
	
}