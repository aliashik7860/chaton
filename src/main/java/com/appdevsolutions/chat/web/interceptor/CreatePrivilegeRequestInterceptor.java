package com.appdevsolutions.chat.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.JsonValidationResponse;
import com.appdevsolutions.chat.web.rest.validation.PrivilegeModelJsonValidator;
import com.appdevsolutions.chat.web.rest.validation.exception.ChatOnJsonParseException;

@Component
public class CreatePrivilegeRequestInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER=LoggerFactory.getLogger(CreatePrivilegeRequestInterceptor.class);
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		LOGGER.info("request is received after : "+request.getRequestURL().toString());
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		LOGGER.info("request is received before : "+request.getRequestURL().toString());
		final String body=JsonProcessor.getBody(request);
		LOGGER.info("request input stream : "+body);
		try{
			request.setAttribute(ChatOnWebConstants.PRIVILEGE_MODEL, PrivilegeModelJsonValidator.validate(body));
			return true;
		}catch(ChatOnJsonParseException chatOnJsonParseException){
			JsonValidationResponse jsonValidationResponse=new JsonValidationResponse(chatOnJsonParseException.getRequired(), chatOnJsonParseException.getMessage());
			JsonProcessor.writeJsonValidationFailedResponse(request, response, jsonValidationResponse);
			return false;
		}
	}
}
