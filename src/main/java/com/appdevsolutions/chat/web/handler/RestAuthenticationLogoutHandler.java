package com.appdevsolutions.chat.web.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnResponse;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.Response;
import com.appdevsolutions.chat.web.model.ResponseStatus;
import com.appdevsolutions.chat.web.model.SecureUserModel;
import com.appdevsolutions.chat.web.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RestAuthenticationLogoutHandler implements LogoutSuccessHandler{

	private static final Logger LOGGER=LoggerFactory.getLogger(RestAuthenticationLogoutHandler.class);

	@Autowired
	UserService userService;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		LOGGER.info("logout success from : "+authentication.getName());
		final SecureUserModel secureUserModel=(SecureUserModel)authentication.getPrincipal();
		try {
			userService.setLastLoginByUserId(secureUserModel.getId(),LocalDateTime.now());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.OK.toString(),HttpStatus.OK,ResponseStatus.SUCCESS,null);
			processResponse(chatOnResponse, response);
		} catch (UserNotFoundException userNotFoundException) {
			final Response responseBean=new Response(ChatOnWebConstants.USER_ID, secureUserModel.getId(),userNotFoundException.getCode(), userNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.BAD_REQUEST.toString(),HttpStatus.BAD_REQUEST,ResponseStatus.FAILURE,responseBean);
			processResponse(chatOnResponse, response);
		}
	}
	private void processResponse(ChatOnResponse<?> chatOnResponse,HttpServletResponse response){
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        final ObjectMapper objectMapper=new ObjectMapper();
        //objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try{
        	final Object object=objectMapper.readValue(objectMapper.writeValueAsString(chatOnResponse),Object.class);
        	String json=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        	response.getWriter().write(json.toString());
        	response.getWriter().flush();
        }catch(Exception exception){
        	
        }
	}
}
