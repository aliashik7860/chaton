package com.appdevsolutions.chat.web.config;


import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.appdevsolutions.chat.web.handler.HttpAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true,securedEnabled=true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String LOGIN_PROCESSING_URL="/login";
	private static final String LOGOUT_PROCESSING_URL="/home/logout";
	private static final String REQUEST_METHOD="GET";
	private static final String COOKIE_NAME="JSESSIONID";
	private static final String USERNAME_PARAMETER="username";
	private static final String PASSWORD_PARAMETER="password";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringSecurityConfig.class);
	
	@Autowired
	@Qualifier("secureUserService")
	UserDetailsService userDetailsService;
	
	@Autowired
	HttpAuthenticationEntryPoint httpAuthenticationEntryPoint;
	
	@Autowired
	AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	LogoutSuccessHandler logoutSuccessHandler;
	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception {
		LOGGER.info("configuring custom authentication provider that is DaoAuthenticationProvider");
		//auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(getAuthenticationProvider());
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		LOGGER.info("configuring form login security");
		
		http.csrf().disable()
		.exceptionHandling()
		.authenticationEntryPoint(httpAuthenticationEntryPoint)
		.and()
		//.addFilterBefore(getMyUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
		.authorizeRequests()
		.antMatchers("/resources/**").permitAll()
        .antMatchers("/","/loginPage","/indexPage","/signUpPage","/signUp").permitAll()
        .antMatchers("/home/*").hasRole("USER")
        .and()
        
        //.addFilterBefore(getMyFilter(),UsernamePasswordAuthenticationFilter.class )
		.formLogin()
		.loginProcessingUrl(LOGIN_PROCESSING_URL)
		.usernameParameter(USERNAME_PARAMETER)
		.passwordParameter(PASSWORD_PARAMETER)
		.successHandler(authenticationSuccessHandler)
        .failureHandler(authenticationFailureHandler)
        //.permitAll()
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_PROCESSING_URL,REQUEST_METHOD))
        .deleteCookies(COOKIE_NAME)
        .logoutSuccessHandler(logoutSuccessHandler)
        .and()
        .sessionManagement()
        .maximumSessions(1)
        .sessionRegistry(getSessionRegistry());
	}
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		final ProviderManager providerManager=new ProviderManager(Arrays.asList(getAuthenticationProvider()));
		return providerManager;
	}
	@Bean 
	public AuthenticationProvider getAuthenticationProvider() {
		LOGGER.info("configuring custom daoAuthenticationProvider");
		final DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
		LOGGER.info("setting custom userDetailsService");
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return daoAuthenticationProvider;
	}
	
	/*@Bean
	public SessionCreatedListener getSessionCreatedListener(){
		return new SessionCreatedListener();
	}*/
	
	@Bean(name="sessionRegistry")
	public SessionRegistry getSessionRegistry(){
		return new SessionRegistryImpl();
	}
	/*@Bean
	public MyUsernamePasswordAuthenticationFilter getMyUsernamePasswordAuthenticationFilter() throws Exception{
		final MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter=new MyUsernamePasswordAuthenticationFilter();
		myUsernamePasswordAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(LOGIN_PROCESSING_URL,"POST"));
		myUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
		myUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		myUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
		myUsernamePasswordAuthenticationFilter.setUsernameParameter(USERNAME_PARAMETER);
		myUsernamePasswordAuthenticationFilter.setPasswordParameter(PASSWORD_PARAMETER);
		return myUsernamePasswordAuthenticationFilter;
	}*/
	
	@Bean
	public MyFilter getMyFilter() throws Exception{
		MyFilter myFilter=new MyFilter();
		myFilter.setAuthenticationManager(authenticationManagerBean());
		myFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		myFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
		//myFilter.
		return myFilter;
	}
}