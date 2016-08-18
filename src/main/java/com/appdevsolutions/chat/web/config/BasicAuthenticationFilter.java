package com.appdevsolutions.chat.web.config;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnResponse;
import com.appdevsolutions.chat.web.model.Response;
import com.appdevsolutions.chat.web.model.ResponseStatus;
import com.appdevsolutions.chat.web.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebFilter("/login")
public final class BasicAuthenticationFilter implements Filter {
	
	@Autowired
	UserService userService;
	
	private static final String AUTHENTICATION_HEADER = "Authorization";
    public BasicAuthenticationFilter() {
       
    }
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String authCredentials = httpServletRequest.getHeader(AUTHENTICATION_HEADER);
			try{
				userService.basicAuthentication(authCredentials);
				filterChain.doFilter(request, response);
			}catch(UserNotFoundException userNotFoundException){
				if (response instanceof HttpServletResponse) {
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					httpServletResponse.setContentType("application/json");
					httpServletResponse.setCharacterEncoding("UTF-8");
			        final Response failureBean=new Response("", "","", userNotFoundException.getMessage());
			        ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), httpServletRequest.getRequestURL().toString(),HttpStatus.UNAUTHORIZED.toString(),HttpStatus.UNAUTHORIZED,ResponseStatus.FAILURE,failureBean);
				    final ObjectMapper objectMapper=new ObjectMapper();
				    final Object object=objectMapper.readValue(objectMapper.writeValueAsString(chatOnResponse),Object.class);
				    String json=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
				    httpServletResponse.getWriter().write(json.toString());
				    httpServletResponse.getWriter().flush();
				}
			}
		}
	}
	public void init(FilterConfig fConfig) throws ServletException {
		
	}
	public void destroy() {
		
	}

}
