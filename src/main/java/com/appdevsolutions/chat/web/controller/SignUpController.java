package com.appdevsolutions.chat.web.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appdevsolutions.chat.web.exception.UserAlreadyExistException;
import com.appdevsolutions.chat.web.model.ChatOnResponse;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.Response;
import com.appdevsolutions.chat.web.model.ResponseStatus;
import com.appdevsolutions.chat.web.model.UserModel;
import com.appdevsolutions.chat.web.service.UserService;

@Controller
public class SignUpController{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(SignUpController.class);
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = ChatOnWebConstants.URL_SIGN_UP, method = RequestMethod.POST,produces="application/json",consumes="application/json")
	@ResponseBody
	public ResponseEntity<?> register(HttpServletRequest request) {
		LOGGER.info("/signup request is accepted");
		final UserModel userModel=(UserModel)request.getAttribute(ChatOnWebConstants.USER_MODEL);
		request.removeAttribute(ChatOnWebConstants.USER_MODEL);
		//request.getSession(false).invalidate();
		try {
			//redisTemplate.opsForValue();
			userService.saveUser(userModel);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch(UserAlreadyExistException userAlreadyExistException) {
			final Response response=new Response(ChatOnWebConstants.USERNAME, userModel.getUsername(),userAlreadyExistException.getCode(), userAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.BAD_REQUEST.toString(),HttpStatus.BAD_REQUEST,ResponseStatus.FAILURE,response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_SIGN_UP_ADMIN,method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> registerAdmin(HttpServletRequest request){
		final UserModel userModel=(UserModel)request.getAttribute(ChatOnWebConstants.USER_MODEL);
		request.removeAttribute(ChatOnWebConstants.USER_MODEL);
		try {
			userService.saveAdminUser(userModel);
			final ChatOnResponse<String> chatOnResponse=new ChatOnResponse<String>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.OK.toString(),HttpStatus.OK,ResponseStatus.SUCCESS,"Admin User created successfully");
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch(UserAlreadyExistException userAlreadyExistException) {
			final Response response=new Response(ChatOnWebConstants.USERNAME, userModel.getUsername(),userAlreadyExistException.getCode(), userAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.BAD_REQUEST.toString(),HttpStatus.BAD_REQUEST,ResponseStatus.FAILURE,response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
}