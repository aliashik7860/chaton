package com.appdevsolutions.chat.web.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.JsonValidationResponse;
import com.appdevsolutions.chat.web.rest.validation.UpdateEmailTemplateFromByIdJsonValidator;
import com.appdevsolutions.chat.web.rest.validation.exception.ChatOnJsonParseException;

@Component
public class UpdateEmailTemplateFromByIdRequestInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER=LoggerFactory.getLogger(UpdateEmailTemplateFromByIdRequestInterceptor.class);
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		LOGGER.info("request is received before : "+request.getRequestURL().toString());
		final String body=JsonProcessor.getBody(request);
		LOGGER.info("request input stream : "+body);
		try{
			final Map<String, String> map=UpdateEmailTemplateFromByIdJsonValidator.validate(body);
			LOGGER.info("request updateEmailTemplateFromById: "+map);
			request.setAttribute(ChatOnWebConstants.UPDATE_EMAIL_TEMPLATE_FROM_BY_ID, map);
			return true;
		}catch(ChatOnJsonParseException chatOnJsonParseException){
			final JsonValidationResponse jsonValidationResponse=new JsonValidationResponse(chatOnJsonParseException.getRequired(), chatOnJsonParseException.getMessage());
			JsonProcessor.writeJsonValidationFailedResponse(request, response, jsonValidationResponse);
			return false;
		}
	}
}