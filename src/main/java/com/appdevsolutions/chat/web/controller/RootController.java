package com.appdevsolutions.chat.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.appdevsolutions.chat.web.model.ChatOnWebConstants;

@Controller
public class RootController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(RootController.class);
	
	@RequestMapping(value=ChatOnWebConstants.URL_ROOT,method=RequestMethod.GET)
	public String indexPage(){
		LOGGER.info("home page requested");
		return "/indexPage";
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_LOGIN_PAGE,method=RequestMethod.GET)
	public String loginPage() {
		LOGGER.info("login page requested");
	    return "/loginPage";
	}
	@RequestMapping(value=ChatOnWebConstants.URL_SIGN_UP_PAGE,method=RequestMethod.GET)
	public String signUpPage(){
		LOGGER.info("signup page requested");
		return "/signUpPage";
	}
}