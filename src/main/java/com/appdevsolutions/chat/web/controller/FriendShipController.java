package com.appdevsolutions.chat.web.controller;

import java.time.LocalDateTime;
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

import com.appdevsolutions.chat.web.exception.FriendShipAlreadyExistException;
import com.appdevsolutions.chat.web.exception.FriendShipNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnResponse;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.FriendShipModel;
import com.appdevsolutions.chat.web.model.Response;
import com.appdevsolutions.chat.web.model.ResponseStatus;
import com.appdevsolutions.chat.web.service.FriendShipService;

@Controller
public class FriendShipController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(FriendShipController.class);
	
	@Autowired
	FriendShipService friendShipService;
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_FRIEND_SHIP_CREATE,method=RequestMethod.POST)
	public ResponseEntity<?> createFriendShipModel(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		FriendShipModel friendShipModel=(FriendShipModel)request.getAttribute(ChatOnWebConstants.FRIEND_SHIP_MODEL);
		request.removeAttribute(ChatOnWebConstants.FRIEND_SHIP_MODEL);
		try {
			friendShipService.createFriendShip(friendShipModel);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (UserNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (FriendShipAlreadyExistException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_FRIEND_SHIP_BY_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable String friendShipId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<FriendShipModel>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, friendShipService.getById(friendShipId));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (FriendShipNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_FRIEND_SHIP_BY_ALL,method=RequestMethod.GET)
	public ResponseEntity<?> getByAll(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<FriendShipModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, friendShipService.getAll());
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_FRIEND_SHIP_BY_ALL_UNFRIENDS,method=RequestMethod.GET)
	public ResponseEntity<?> getAllUnFriends(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<FriendShipModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, friendShipService.getAllUnFriends());
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_FRIEND_SHIP_BY_ALL_FRIENDS,method=RequestMethod.GET)
	public ResponseEntity<?> getAllFriends(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<FriendShipModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, friendShipService.getAllFriends());
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_FRIEND_SHIP_BY_ALL_SENT_REQUESTS_BY_USER_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getAllSentRequestsByUserId(@PathVariable String userId,HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<FriendShipModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, friendShipService.getAllSentRequestsByUserId(userId));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (UserNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_FRIEND_SHIP_BY_ALL_RECEIVED_REQUESTS_BY_USER_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getAllReceivedRequestsByUserId(@PathVariable String userId,HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<FriendShipModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, friendShipService.getAllReceivedRequestsByUserId(userId));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (UserNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_FRIEND_SHIP_BY_ALL_FRIENDS_BY_USER_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getAllFriendsByUserId(@PathVariable String userId,HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<FriendShipModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, friendShipService.getAllFriendsByUserId(userId));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (UserNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_FRIEND_SHIP_BY_ALL_BLOCKED_FRIENDS,method=RequestMethod.GET)
	public ResponseEntity<?> getAllBlockedFriends(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<FriendShipModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, friendShipService.getAllBlockedFriends());
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_FRIEND_SHIP_BY_ALL_BLOCKED_FRIENDS_BY_SENDER_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getAllBlockedFriendsBySenderId(@PathVariable String senderId,HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<FriendShipModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, friendShipService.getAllBlockedFriendsBySenderId(senderId));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (UserNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_FRIEND_SHIP_BY_ALL_BLOCKED_FRIENDS_BY_RECEIVER_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getAllBlockedFriendsByReceiverId(@PathVariable String receiverId,HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<FriendShipModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, friendShipService.getAllBlockedFriendsByReceiverId(receiverId));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (UserNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_FRIEND_SHIP_BY_ID,method=RequestMethod.DELETE)
	public ResponseEntity<?> removeById(@PathVariable String friendShipId,HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			friendShipService.removeById(friendShipId);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (FriendShipNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_FRIEND_SHIP_BY_ALL_SENT_REQUESTS_BY_USER_ID,method=RequestMethod.DELETE)
	public ResponseEntity<?> removeAllSentRequestsByUserId(@PathVariable String userId,HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			friendShipService.removeAllSentRequestsByUserId(userId);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (UserNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_FRIEND_SHIP_BY_ALL_RECEIVED_REQUESTS_BY_USER_ID,method=RequestMethod.DELETE)
	public ResponseEntity<?> removeAllReceivedRequestsByUserId(@PathVariable String userId,HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			friendShipService.removeAllReceivedRequestsByUserId(userId);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (UserNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_FRIEND_SHIP_MAKE_BY_ID,method=RequestMethod.PUT)
	public ResponseEntity<?> makeFriendById(@PathVariable String friendShipId,HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			friendShipService.makeFriendById(friendShipId);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (FriendShipNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (FriendShipAlreadyExistException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_FRIEND_SHIP_MAKE_BY_USER_ID,method=RequestMethod.PUT)
	public ResponseEntity<?> makeFriendsByUserId(@PathVariable String userId,HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			friendShipService.makeFriendByUserId(userId);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (FriendShipNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (UserNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_FRIEND_SHIP_UNMAKE_BY_ID,method=RequestMethod.PUT)
	public ResponseEntity<?> makeUnFriendById(@PathVariable String friendShipId,HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			friendShipService.makeUnFriendById(friendShipId);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (FriendShipNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}