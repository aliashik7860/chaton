package com.appdevsolutions.chat.web.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.appdevsolutions.chat.web.exception.RoleAlreadyExistException;
import com.appdevsolutions.chat.web.exception.RoleNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnResponse;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.Response;
import com.appdevsolutions.chat.web.model.ResponseStatus;
import com.appdevsolutions.chat.web.model.RoleModel;
import com.appdevsolutions.chat.web.service.RoleService;

@Controller
public class RoleController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	RoleService roleService;
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_ROLE_CREATE,method=RequestMethod.POST)
	public ResponseEntity<?> createRole(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		final RoleModel roleModel=(RoleModel)request.getAttribute(ChatOnWebConstants.ROLE_MODEL);
		request.removeAttribute(ChatOnWebConstants.ROLE_MODEL);
		try {
			roleService.save(roleModel);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (UserNotFoundException userNotFoundException) {
			LOGGER.error("User not found : ",userNotFoundException);
			final Response response=new Response(userNotFoundException.getRejectType(), userNotFoundException.getRejectValue(),userNotFoundException.getCode(), userNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (RoleAlreadyExistException roleAlreadyExistException) {
			LOGGER.error("User not found : ",roleAlreadyExistException);
			final Response response=new Response(roleAlreadyExistException.getRejectType(), roleAlreadyExistException.getRejectValue(),roleAlreadyExistException.getCode(),roleAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_ROLE_BY_ID,method=RequestMethod.DELETE)
	public ResponseEntity<?> removeById(@PathVariable String id, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			roleService.removeById(id);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<String>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,null );
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (RoleNotFoundException roleNotFoundException) {
			LOGGER.error("User not found : ",roleNotFoundException);
			final Response response=new Response(roleNotFoundException.getRejectType(), roleNotFoundException.getRejectValue(),roleNotFoundException.getCode(), roleNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_ROLE_BY_NAME,method=RequestMethod.DELETE)
	public ResponseEntity<?> removeByName(@PathVariable String name, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			roleService.removeByName(name);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<String>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,null );
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (RoleNotFoundException roleNotFoundException) {
			LOGGER.error("User not found : ",roleNotFoundException);
			final Response response=new Response(roleNotFoundException.getRejectType(), roleNotFoundException.getRejectValue(),roleNotFoundException.getCode(), roleNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_ROLE_BY_USER_ID,method=RequestMethod.DELETE)
	public ResponseEntity<?> removeByUserId(@PathVariable String userId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			roleService.removeByUserId(userId);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<String>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,null );
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (UserNotFoundException roleNotFoundException) {
			LOGGER.error("User not found : ",roleNotFoundException);
			final Response response=new Response(roleNotFoundException.getRejectType(), roleNotFoundException.getRejectValue(), roleNotFoundException.getCode(),roleNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		} catch (RoleNotFoundException roleNotFoundException) {
			LOGGER.error("User not found : ",roleNotFoundException);
			final Response response=new Response(roleNotFoundException.getRejectType(), roleNotFoundException.getRejectValue(), roleNotFoundException.getCode(), roleNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_ROLE_ALL,method=RequestMethod.GET)
	public ResponseEntity<?> getAll( HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<RoleModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, roleService.getAll());
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_ROLE_BY_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable String id, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<RoleModel>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, roleService.getById(id));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (RoleNotFoundException roleNotFoundException) {
			LOGGER.error("User not found : ",roleNotFoundException);
			final Response response=new Response(roleNotFoundException.getRejectType(), roleNotFoundException.getRejectValue(),roleNotFoundException.getCode(), roleNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_ROLE_BY_NAME,method=RequestMethod.GET)
	public ResponseEntity<?> getByName(@PathVariable String name, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<RoleModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, roleService.getByName(name));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (RoleNotFoundException roleNotFoundException) {
			LOGGER.error("User not found : ",roleNotFoundException);
			final Response response=new Response(roleNotFoundException.getRejectType(), roleNotFoundException.getRejectValue(),roleNotFoundException.getCode(), roleNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_ROLE_BY_USER_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getByUserId(@PathVariable String userId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<RoleModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, roleService.getByUserId(userId));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (UserNotFoundException roleNotFoundException) {
			LOGGER.error("User not found : ",roleNotFoundException);
			final Response response=new Response(roleNotFoundException.getRejectType(), roleNotFoundException.getRejectValue(), roleNotFoundException.getCode(),roleNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		} catch (RoleNotFoundException roleNotFoundException) {
			LOGGER.error("User not found : ",roleNotFoundException);
			final Response response=new Response(roleNotFoundException.getRejectType(), roleNotFoundException.getRejectValue(),roleNotFoundException.getCode(), roleNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_ROLE_UPDATE_NAME_BY_ID,method=RequestMethod.PUT)
	public ResponseEntity<?> update(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		@SuppressWarnings("unchecked")
		final Map<String, String> map=(Map<String, String>)request.getAttribute(ChatOnWebConstants.UPDATE_ROLE_MAP);
		request.removeAttribute(ChatOnWebConstants.UPDATE_ROLE_MAP);
		try {
			roleService.updateName(map.get(ChatOnWebConstants.ROLE_ID),map.get(ChatOnWebConstants.OLD_ROLE_NAME),map.get(ChatOnWebConstants.NEW_ROLE_NAME));
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (RoleNotFoundException userNotFoundException) {
			LOGGER.error("User not found : ",userNotFoundException);
			final Response response=new Response(userNotFoundException.getRejectType(), userNotFoundException.getRejectValue(),userNotFoundException.getCode(), userNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (RoleAlreadyExistException roleAlreadyExistException) {
			LOGGER.error("Role already exist : ",roleAlreadyExistException);
			final Response response=new Response(roleAlreadyExistException.getRejectType(), roleAlreadyExistException.getRejectValue(),roleAlreadyExistException.getCode(),roleAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
