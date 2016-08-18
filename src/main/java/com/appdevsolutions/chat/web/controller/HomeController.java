package com.appdevsolutions.chat.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.appdevsolutions.chat.web.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	UserService userService;
	
	@InitBinder
    private void initBinder(WebDataBinder binder,HttpSession session) {
		/*final UserLoginResponse userLoginResponse=(UserLoginResponse)session.getAttribute("loginSession");
		final UpdatePasswordModel userChangePasswordRequest=(UpdatePasswordModel)binder.getTarget();
		userChangePasswordRequest.setCurrentEmailId(userLoginResponse.getUsername());*/
	}
}
