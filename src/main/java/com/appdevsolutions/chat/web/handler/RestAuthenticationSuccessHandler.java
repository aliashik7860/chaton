package com.appdevsolutions.chat.web.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	private static final Logger LOGGER=LoggerFactory.getLogger(RestAuthenticationSuccessHandler.class);
	@ResponseBody
	@ResponseStatus(value=HttpStatus.OK)
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {
		LOGGER.info("getting authentication success request");
		LOGGER.info("response code : "+HttpServletResponse.SC_OK);
		//SecurityContext securityContext=SecurityContextHolder.getContext();
		//Authentication authentication2=securityContext.getAuthentication();
		//response.sendError(HttpServletResponse.SC_OK);
	}

}
