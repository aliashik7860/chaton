package com.appdevsolutions.chat.web.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.appdevsolutions.chat.web.model.ChatOnResponse;
import com.appdevsolutions.chat.web.model.Response;
import com.appdevsolutions.chat.web.model.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler{

	private static final Logger LOGGER=LoggerFactory.getLogger(RestAuthenticationFailureHandler.class);
	//@ResponseBody
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authenticationException)
			throws IOException, ServletException {
		LOGGER.info("getting authentication failure request");
		LOGGER.info("response code : "+HttpServletResponse.SC_UNAUTHORIZED+" && error message : "+authenticationException.getMessage());
		//response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authenticationException.getMessage());
		//throw authenticationException;
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        final Response failureBean=new Response("", "","", authenticationException.getMessage());
        ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(),HttpStatus.UNAUTHORIZED.toString(),HttpStatus.UNAUTHORIZED,ResponseStatus.FAILURE,failureBean);
	    final ObjectMapper objectMapper=new ObjectMapper();
	    final Object object=objectMapper.readValue(objectMapper.writeValueAsString(chatOnResponse),Object.class);
	    String json=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        response.getWriter().write(json.toString());
        response.getWriter().flush();
        LOGGER.info(json.toString());
	}
}