package com.appdevsolutions.chat.web.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appdevsolutions.chat.web.exception.AccessDeniedException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.exception.UserUpdatePasswordException;
import com.appdevsolutions.chat.web.model.ChatOnResponse;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.Response;
import com.appdevsolutions.chat.web.model.ResponseStatus;
import com.appdevsolutions.chat.web.model.SecureUserModel;
import com.appdevsolutions.chat.web.model.UserModel;
import com.appdevsolutions.chat.web.service.UserService;


@Controller
public class LoginController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	SessionRegistry sessionRegistry;
	
	@InitBinder
    private void initBinder(WebDataBinder binder) {
        //binder.addValidators(loginUsernameAndPasswordValidator);
    }
	
	
	/*@Secured("ROLE_ADMIN")*/
	@RequestMapping(value=ChatOnWebConstants.URL_HOME,method=RequestMethod.GET)
	public String home(HttpServletRequest request){
		LOGGER.info("request received for homepage via url : "+request.getRequestURL().toString());
		return "homePage";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_USERS_LIVE_ALL,method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> liverAllUsers(HttpServletRequest request){
		LOGGER.info("request recieved from url : "+request.getRequestURL().toString());
		try{
			final ChatOnResponse<Set<UserModel>> chatOnResponse=new ChatOnResponse<Set<UserModel>>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.OK.toString(),HttpStatus.OK,ResponseStatus.SUCCESS,getLiveUserModels());
			return new ResponseEntity<ChatOnResponse<Set<UserModel>>>(chatOnResponse,HttpStatus.OK);
		}catch(AccessDeniedException accessDeniedException){
			LOGGER.error("error at : ",accessDeniedException);
			final Response response=new Response("", "",accessDeniedException.getCode(), accessDeniedException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.UNAUTHORIZED.toString(),HttpStatus.UNAUTHORIZED,ResponseStatus.FAILURE,response);
			LOGGER.info("response from url : "+request.getRequestURL().toString()+" "+response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.UNAUTHORIZED);
		}
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_USERS_LIVE_COUNT,method=RequestMethod.GET)
	public ResponseEntity<?> liveCount(HttpServletRequest request){
		try{
			final Map<String, Integer> map=new HashMap<String,Integer>();
			map.put("count", getLiveUserModels().size());
			final ChatOnResponse<Map<String, Integer>> chatOnResponse=new ChatOnResponse<Map<String,Integer>>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.OK.toString(),HttpStatus.OK,ResponseStatus.SUCCESS,map);
			return new ResponseEntity<ChatOnResponse<Map<String,Integer>>>(chatOnResponse,HttpStatus.OK);
		}catch(AccessDeniedException accessDeniedException){
			final Response response=new Response("", "",accessDeniedException.getCode(), accessDeniedException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.UNAUTHORIZED.toString(),HttpStatus.UNAUTHORIZED,ResponseStatus.FAILURE,response);
			LOGGER.info("response from url : "+request.getRequestURL().toString()+" "+response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.UNAUTHORIZED);
		}
	}
	
	@Secured(value={"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_USERS_LIVE_ME,method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> currentlyLoginUser(HttpServletRequest request){
		try{
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.OK.toString(),HttpStatus.OK,ResponseStatus.SUCCESS,userService.liveMe());
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch(UserNotFoundException userNotFoundException){
			LOGGER.error("User not found : ",userNotFoundException);
			final Response response=new Response(userNotFoundException.getRejectType(), userNotFoundException.getRejectValue(),userNotFoundException.getCode(), userNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_USERS_LIVE_OTHER_THAN_ME,method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> liveOtherThanMe(HttpServletRequest request){
		try{
			final Set<UserModel> liveUserModels=getLiveUserModels();
			liveUserModels.remove(getLiveUserModel());
			final ChatOnResponse<Set<UserModel>> chatOnResponse=new ChatOnResponse<Set<UserModel>>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.OK.toString(),HttpStatus.OK,ResponseStatus.SUCCESS,liveUserModels);
			return new ResponseEntity<ChatOnResponse<Set<UserModel>>>(chatOnResponse,HttpStatus.OK);
		}catch(AccessDeniedException accessDeniedException){
			final Response response=new Response("", "",accessDeniedException.getCode(), accessDeniedException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.UNAUTHORIZED.toString(),HttpStatus.UNAUTHORIZED,ResponseStatus.FAILURE,response);
			LOGGER.info("response from url : "+request.getRequestURL().toString()+" "+response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.UNAUTHORIZED);
		}
	}
	
	@Secured(value={"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_USERS_LIVE_AND_NOT_LIVE,method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> liveAndNotLiveNotMe(HttpServletRequest request){
		final ChatOnResponse<List<UserModel>> chatOnResponse=new ChatOnResponse<List<UserModel>>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.OK.toString(),HttpStatus.OK,ResponseStatus.SUCCESS,userService.allUsers());
		return new ResponseEntity<ChatOnResponse<List<UserModel>>>(chatOnResponse,HttpStatus.OK);
	}
	
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_USERS_UPDATE_PASSWORD,method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updatePassword(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		@SuppressWarnings("unchecked")
		final Map<String,String> map=(Map<String, String>)request.getAttribute(ChatOnWebConstants.UPDATE_PASSWORD_MAP);
		request.removeAttribute(ChatOnWebConstants.UPDATE_PASSWORD_MAP);
		LOGGER.info("updatePasswordModel from request : "+map);
		try{
			userService.updateUserPassword(map.get(ChatOnWebConstants.USER_ID),map.get(ChatOnWebConstants.OLD_PASSWORD),map.get(ChatOnWebConstants.NEW_PASSWORD));
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch(UserNotFoundException userNotFoundException){
			LOGGER.error("User not found : ",userNotFoundException);
			final Response response=new Response(userNotFoundException.getRejectType(), userNotFoundException.getRejectValue(),userNotFoundException.getCode(), userNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch(UserUpdatePasswordException userUpdatePasswordException){
			LOGGER.error("User not found : ",userUpdatePasswordException);
			final Response response=new Response(userUpdatePasswordException.getRejectedType(), userUpdatePasswordException.getRejectedValue(),userUpdatePasswordException.getCode(), userUpdatePasswordException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_USERS_ALL,method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> userByAll(HttpServletRequest request){
		/*try{
			UserModel userModel=getLiveUserModel();*/
			final ChatOnResponse<List<UserModel>> chatOnResponse=new ChatOnResponse<List<UserModel>>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.OK.toString(),HttpStatus.OK,ResponseStatus.SUCCESS,userService.allUsers());
			return new ResponseEntity<ChatOnResponse<List<UserModel>>>(chatOnResponse,HttpStatus.OK);
		/*}catch(AccessDeniedException accessDeniedException){
			return new ResponseEntity<String>(accessDeniedException.getMessage(),HttpStatus.OK);
		}*/
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_USERS_USER_BY_USER_ID,method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getUserById(@PathVariable(ChatOnWebConstants.USER_ID) String userId,HttpServletRequest request){
		try {
			final ChatOnResponse<UserModel> chatOnResponse=new ChatOnResponse<UserModel>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.OK.toString(),HttpStatus.OK,ResponseStatus.SUCCESS,userService.findUserById(userId));
			return new ResponseEntity<ChatOnResponse<UserModel>>(chatOnResponse,HttpStatus.OK);
		}catch(UserNotFoundException userNotFoundException) {
			final Response response=new Response(ChatOnWebConstants.USER_ID, userId,userNotFoundException.getCode(), userNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.BAD_REQUEST.toString(),HttpStatus.BAD_REQUEST,ResponseStatus.FAILURE,response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@Secured(value={"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_USERS_CHECK_LIVE_BY_USER_ID,method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> checkLive(@PathVariable(ChatOnWebConstants.USER_ID) String userId,HttpServletRequest request){
		try{
			if(checkLiveByUserId(userId)){
				LOGGER.info("returning response from url : "+request.getRequestURL().toString()+" "+userId);
				final ChatOnResponse<Boolean> chatOnResponse=new ChatOnResponse<Boolean>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.OK.toString(),HttpStatus.OK,ResponseStatus.SUCCESS,new Boolean(true));
				return new ResponseEntity<ChatOnResponse<Boolean>>(chatOnResponse,HttpStatus.OK);
			}else{
				LOGGER.info("returning response from url : "+request.getRequestURL().toString()+" "+userId);
				final ChatOnResponse<Boolean> chatOnResponse=new ChatOnResponse<Boolean>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.OK.toString(),HttpStatus.OK,ResponseStatus.SUCCESS,new Boolean(false));
				return new ResponseEntity<ChatOnResponse<Boolean>>(chatOnResponse,HttpStatus.OK);
			}
		}catch(AccessDeniedException accessDeniedException){
			final Response response=new Response("", "",accessDeniedException.getCode(), accessDeniedException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.UNAUTHORIZED.toString(),HttpStatus.UNAUTHORIZED,ResponseStatus.FAILURE,response);
			LOGGER.info("response from url : "+request.getRequestURL().toString()+" "+response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.UNAUTHORIZED);
		}
	}
	
	
	/*@RequestMapping(value=ChatOnWebConstants.URL_HOME_USERS_URSR_BY_USERNAME,method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getUser(@PathVariable(ChatOnWebConstants.USERNAME) String username,HttpServletRequest request){
		try {
			final ChatOnResponse<UserModel> chatOnResponse=new ChatOnResponse<UserModel>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.OK.toString(),HttpStatus.OK,ResponseStatus.SUCCESS,userService.findUserByUsername(username));
			return new ResponseEntity<ChatOnResponse<UserModel>>(chatOnResponse,HttpStatus.OK);
		}catch(UserNotFoundException userNotFoundException) {
			final Response response=new Response(ChatOnWebConstants.USERNAME, username,userNotFoundException.getCode(), userNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.NOT_FOUND.toString(),HttpStatus.NOT_FOUND,ResponseStatus.FAILURE,response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.NOT_FOUND);
		}
	}*/
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_USERS_USER_BY_USER_ID,method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deleteUserById(@PathVariable String userId,HttpServletRequest request){
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.OK.toString(),HttpStatus.OK,ResponseStatus.SUCCESS,null);
			userService.deleteUserById(userId);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch(UserNotFoundException userNotFoundException) {
			final Response response=new Response(ChatOnWebConstants.USER_ID, userId,userNotFoundException.getCode(), userNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.NOT_FOUND.toString(),HttpStatus.NOT_FOUND,ResponseStatus.FAILURE,response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.NOT_FOUND);
		}
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_USERS_URSR_BY_USERNAME,method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deleteUserByUsername(@PathVariable String username,HttpServletRequest request){
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.OK.toString(),HttpStatus.OK,ResponseStatus.SUCCESS,null);
			userService.deleteUserByUsername(username);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch(UserNotFoundException userNotFoundException) {
			final Response response=new Response(ChatOnWebConstants.USERNAME, username, userNotFoundException.getCode(), userNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.NOT_FOUND.toString(),HttpStatus.NOT_FOUND,ResponseStatus.FAILURE,response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.NOT_FOUND);
		}
	}
	
	private UserModel getLiveUserModel() throws AccessDeniedException{
		final Object object=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(object instanceof SecureUserModel){
			final SecureUserModel secureUserModel=(SecureUserModel)object;
			final UserModel liveUserModel=new UserModel(secureUserModel.getId(), secureUserModel.getUsername(), null,"",null, secureUserModel.getLastLogin(),null, secureUserModel.getMobileNumber(), secureUserModel.getAddress(),true,null);
			return liveUserModel;
		}else{
			throw new AccessDeniedException("Session has expired or user is not authenticated");
		}
	}
	private Set<UserModel> getLiveUserModels() throws AccessDeniedException{
		final Set<UserModel> liveUserModels=new HashSet<UserModel>(sessionRegistry.getAllPrincipals().size());
		for(final Object principal:sessionRegistry.getAllPrincipals()){
			if(principal instanceof SecureUserModel){
				final SecureUserModel secureUserModel=(SecureUserModel)principal;
				final UserModel userModel=new UserModel(secureUserModel.getId(), secureUserModel.getUsername(), null,"",null, secureUserModel.getLastLogin(),null, secureUserModel.getMobileNumber(), secureUserModel.getAddress(),true,null);
				liveUserModels.add(userModel);
			}else{
				throw new AccessDeniedException("Session has expired or user is not authenticated");
			}
		}
		return liveUserModels;
	}
	
	private UserModel getUserModelByUserId(String userId){
		final UserModel userModel=new UserModel(userId, "", null,"",null, null,null, "",null, false,null);
		return userModel;
	}
	private boolean checkLiveByUserId(String userId)throws AccessDeniedException{
		final Set<UserModel> liveUserModels=getLiveUserModels();
		if(liveUserModels.contains(getUserModelByUserId(userId))){
			return true;
		}else{
			return false;
		}
	}
}